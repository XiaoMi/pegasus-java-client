// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.rpc.async;

import com.xiaomi.infra.pegasus.base.error_code;
import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.base.gpid;
import com.xiaomi.infra.pegasus.base.rpc_address;
import com.xiaomi.infra.pegasus.operator.client_operator;
import com.xiaomi.infra.pegasus.operator.query_cfg_operator;
import com.xiaomi.infra.pegasus.replication.partition_configuration;
import com.xiaomi.infra.pegasus.replication.query_cfg_request;
import com.xiaomi.infra.pegasus.replication.query_cfg_response;
import com.xiaomi.infra.pegasus.tools.Toollet;
import com.xiaomi.infra.pegasus.tools.Tools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class MetaSessionTest {

  @Before
  public void before() throws Exception {}

  @After
  public void after() throws Exception {
    rpc_address addr = new rpc_address();
    addr.fromString("127.0.0.1:34602");
    Toollet.tryStartServer(addr);
  }

  private static void ensureNotLeader(rpc_address addr) {
    Toollet.closeServer(addr);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    Toollet.tryStartServer(addr);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /** Method: connect() */
  @Test
  public void testConnect() throws Exception {
    // test: first connect to a wrong server
    // then it forward to the right server
    // then the wrong server crashed
    System.out.println("test meta connection");
    String[] addr_list = {"127.0.0.1:34602", "127.0.0.1:34603", "127.0.0.1:34601"};
    ClusterManager manager = new ClusterManager(1000, 4, false, null, 60, addr_list);
    MetaSession session = manager.getMetaSession();

    rpc_address addr = new rpc_address();
    addr.fromString("127.0.0.1:34602");
    ensureNotLeader(addr);

    ArrayList<FutureTask<Void>> callbacks = new ArrayList<FutureTask<Void>>();
    for (int i = 0; i < 1000; ++i) {
      query_cfg_request req = new query_cfg_request("temp", new ArrayList<Integer>());
      final client_operator op = new query_cfg_operator(new gpid(-1, -1), req);
      FutureTask<Void> callback =
          new FutureTask<Void>(
              new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                  Assert.assertEquals(error_code.error_types.ERR_OK, op.rpc_error.errno);
                  return null;
                }
              });
      callbacks.add(callback);
      session.asyncQuery(op, callback, 10, 2);
    }

    Toollet.closeServer(addr);
    for (FutureTask<Void> cb : callbacks) {
      try {
        Tools.waitUninterruptable(cb, Integer.MAX_VALUE);
      } catch (ExecutionException e) {
        e.printStackTrace();
        Assert.fail();
      }
    }

    manager.close();
  }

  private rpc_address[] convert(List<ReplicaSession> sessions) {
    rpc_address[] results = new rpc_address[sessions.size()];
    for (int i = 0; i < results.length; i++) {
      results[i] = sessions.get(i).getAddress();
    }
    return results;
  }

  @Test
  public void testResolveHost() throws Exception {
    // ensure meta list keeps consistent with dns.

    System.out.println("test resolve host");
    ClusterManager manager =
        new ClusterManager(
            1000,
            4,
            false,
            null,
            60,
            new String[] {"127.0.0.1:34602", "127.0.0.1:34603", "127.0.0.1:34601"});
    MetaSession session = manager.getMetaSession();
    MetaSession meta = Mockito.spy(session);
    Assert.assertEquals(meta.getMetaList().size(), 3);
    Assert.assertEquals(
        meta.getMetaList().get(0).getAddress(), rpc_address.fromIpPort("127.0.0.1:34602"));
    Assert.assertEquals(
        meta.getMetaList().get(1).getAddress(), rpc_address.fromIpPort("127.0.0.1:34603"));
    Assert.assertEquals(
        meta.getMetaList().get(2).getAddress(), rpc_address.fromIpPort("127.0.0.1:34601"));
    ReplicaSession meta2 = meta.getMetaList().get(0);
    meta2.doConnect();
    while (meta2.getState() != ReplicaSession.ConnState.CONNECTED) {
      Thread.sleep(1);
    }
    Assert.assertEquals(meta2.getState(), ReplicaSession.ConnState.CONNECTED);

    // DNS refreshed
    rpc_address[] addrs = new rpc_address[2];
    addrs[0] = rpc_address.fromIpPort("172.0.0.1:34601");
    addrs[1] = rpc_address.fromIpPort("172.0.0.2:34601");
    Mockito.when(meta.resolve(("localhost:34601"), 2)).thenReturn(addrs);
    Assert.assertArrayEquals(meta.resolve("localhost:34601", 2), addrs);
    meta.resolveHost("localhost:34601");
    Assert.assertArrayEquals(convert(meta.getMetaList()), addrs);
    while (meta2.getState() != ReplicaSession.ConnState.DISCONNECTED) {
      Thread.sleep(1);
    }
    // ensure MetaSession#resolveHost will close unused sessions.
    Assert.assertEquals(meta2.getState(), ReplicaSession.ConnState.DISCONNECTED);

    // unchanged
    meta.resolveHost("localhost:34601");
    Assert.assertArrayEquals(convert(meta.getMetaList()), addrs);

    // DNS refreshed
    addrs = new rpc_address[2];
    addrs[0] = rpc_address.fromIpPort("172.0.0.1:34601");
    addrs[1] = rpc_address.fromIpPort("172.0.0.3:34601");
    Mockito.when(meta.resolve(("localhost:34601"), 2)).thenReturn(addrs);
    meta.resolveHost("localhost:34601");
    Assert.assertArrayEquals(convert(meta.getMetaList()), addrs);

    manager.close();
  }

  @Test
  public void testMetaAllDead() throws Exception {
    System.out.println("test meta all dead");
    ClusterManager manager =
        new ClusterManager(1000, 4, false, null, 60, new String[] {"localhost:34601"});
    MetaSession session = manager.getMetaSession();
    MetaSession meta = Mockito.spy(session);
    // curLeader=0, hostPort="localhost:34601"

    // metaList = 172.0.0.1:34601, 172.0.0.2:34601
    rpc_address[] addrs = new rpc_address[2];
    addrs[0] = rpc_address.fromIpPort("172.0.0.1:34601");
    addrs[1] = rpc_address.fromIpPort("172.0.0.2:34601");
    Mockito.when(meta.resolve(("localhost:34601"), 2)).thenReturn(addrs);
    meta.resolveHost("localhost:34601");
    Assert.assertArrayEquals(convert(meta.getMetaList()), addrs);

    query_cfg_request req = new query_cfg_request("temp", new ArrayList<Integer>());
    client_operator op = new query_cfg_operator(new gpid(-1, -1), req);
    op.rpc_error.errno = error_code.error_types.ERR_SESSION_RESET;
    MetaSession.MetaRequestRound round =
        new MetaSession.MetaRequestRound(
            op,
            new Runnable() {
              @Override
              public void run() {}
            },
            10,
            2,
            meta.getMetaList().get(0));

    // do not retry after a failed QueryMeta.
    Mockito.doNothing().when(meta).retryQueryMeta(round, false);

    // DNS updated.
    rpc_address[] addrs2 = new rpc_address[2];
    addrs2[0] = rpc_address.fromIpPort("172.0.0.3:34601");
    addrs2[1] = rpc_address.fromIpPort("172.0.0.4:34601");
    Mockito.when(meta.resolve(("localhost:34601"), 2)).thenReturn(addrs2);

    // failed to query meta
    meta.onFinishQueryMeta(round);
    // switch curLeader to 1, meta list unchanged.
    Assert.assertArrayEquals(convert(meta.getMetaList()), addrs);
    Integer curLeader = (Integer) FieldUtils.readField(meta, "curLeader", true);
    Assert.assertEquals(curLeader.intValue(), 1);

    meta.setMaxQueryCount(round.maxQueryCount);
    // failed again
    meta.onFinishQueryMeta(round);
    // switch curLeader to 0, meta list updated
    Assert.assertArrayEquals(convert(meta.getMetaList()), addrs2);
    curLeader = (Integer) FieldUtils.readField(meta, "curLeader", true);
    Assert.assertEquals(curLeader.intValue(), 0);

    // retry
    meta.onFinishQueryMeta(round);
    Assert.assertArrayEquals(convert(meta.getMetaList()), addrs2);
  }

  @Test
  public void testMetaForwardRealLeader() throws Exception {
    System.out.println("test meta forward real leader");
    ClusterManager manager =
        new ClusterManager(1000, 4, false, null, 60, new String[] {"localhost:34601"});
    MetaSession session = manager.getMetaSession();
    MetaSession meta = Mockito.spy(session);
    // curLeader=0, hostPort="localhost:34601"

    // metaList = 172.0.0.1:34601, 172.0.0.2:34601
    rpc_address[] addrs = new rpc_address[2];
    addrs[0] = rpc_address.fromIpPort("172.0.0.1:34601");
    addrs[1] = rpc_address.fromIpPort("172.0.0.2:34601");
    Mockito.when(meta.resolve(("localhost:34601"), 2)).thenReturn(addrs);
    meta.resolveHost("localhost:34601");
    Assert.assertArrayEquals(convert(meta.getMetaList()), addrs);

    query_cfg_request req = new query_cfg_request("temp", new ArrayList<Integer>());
    query_cfg_operator op = new query_cfg_operator(new gpid(-1, -1), req);
    op.rpc_error.errno = error_code.error_types.ERR_OK;
    FieldUtils.writeField(op, "response", new query_cfg_response(), true);
    op.get_response().err = new error_code();
    op.get_response().err.errno = error_code.error_types.ERR_FORWARD_TO_OTHERS;
    op.get_response().partitions = Arrays.asList(new partition_configuration[1]);
    op.get_response().partitions.set(0, new partition_configuration());
    op.get_response().partitions.get(0).primary = rpc_address.fromIpPort("172.0.0.3:34601");
    MetaSession.MetaRequestRound round =
        new MetaSession.MetaRequestRound(
            op,
            new Runnable() {
              @Override
              public void run() {}
            },
            10,
            2,
            meta.getMetaList().get(0));

    // do not retry after a failed QueryMeta.
    Mockito.doNothing().when(meta).retryQueryMeta(round, false);

    // failed to query meta
    meta.onFinishQueryMeta(round);

    rpc_address[] addrs2 = Arrays.copyOf(addrs, 3);
    addrs2[2] = rpc_address.fromIpPort("172.0.0.3:34601");

    // forward to 172.0.0.3:34601
    Assert.assertArrayEquals(convert(meta.getMetaList()), addrs2);
    Integer curLeader = (Integer) FieldUtils.readField(meta, "curLeader", true);
    Assert.assertEquals(curLeader.intValue(), 2);
  }

  @Test
  public void testMetaAllChanged() {
    System.out.println("test meta all changed and can auto fresh");
    ClusterManager manager =
        new ClusterManager(1000, 4, false, null, 60, new String[] {"localhost:34601"});
    MetaSession metaMock = Mockito.spy(manager.getMetaSession());

    rpc_address[] errorAddrs = new rpc_address[3];
    errorAddrs[0] = rpc_address.fromIpPort("172.0.0.1:34602");
    errorAddrs[1] = rpc_address.fromIpPort("172.0.0.1:34603");
    errorAddrs[2] = rpc_address.fromIpPort("172.0.0.1:34601");

    List<ReplicaSession> metaList = metaMock.getMetaList();
    metaList.remove(0);
    metaList.add(manager.getReplicaSession(errorAddrs[0]));
    metaList.add(manager.getReplicaSession(errorAddrs[1]));
    metaList.add(manager.getReplicaSession(errorAddrs[2]));

    rpc_address[] rightAddrs = new rpc_address[3];
    rightAddrs[0] = rpc_address.fromIpPort("127.0.0.1:34602");
    rightAddrs[1] = rpc_address.fromIpPort("127.0.0.1:34603");
    rightAddrs[2] = rpc_address.fromIpPort("127.0.0.1:34601");

    Mockito.when(metaMock.resolve("localhost:34601", 2)).thenReturn(rightAddrs);

    query_cfg_request req = new query_cfg_request("temp", new ArrayList<Integer>());
    client_operator op = new query_cfg_operator(new gpid(-1, -1), req);

    metaMock.query(op, 5, 2);
    error_types err = MetaSession.getMetaServiceError(op);
    Assert.assertEquals(error_code.error_types.ERR_OK, err);
  }
}

// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.rpc.async;

import static java.lang.Integer.max;

import com.xiaomi.infra.pegasus.base.rpc_address;
import com.xiaomi.infra.pegasus.client.ClientOptions;
import com.xiaomi.infra.pegasus.metrics.MetricsManager;
import com.xiaomi.infra.pegasus.rpc.Cluster;
import com.xiaomi.infra.pegasus.rpc.InternalTableOptions;
import com.xiaomi.infra.pegasus.rpc.ReplicationException;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;

public class ClusterManager extends Cluster {
  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ClusterManager.class);

  private int operationTimeout;
  private int retryDelay;
  private boolean enableCounter;

  private ConcurrentHashMap<rpc_address, ReplicaSession> replicaSessions;
  private EventLoopGroup metaGroup; // group used for handle meta logic
  private EventLoopGroup replicaGroup; // group used for handle io with replica servers
  private EventLoopGroup tableGroup; // group used for handle table logic
  private String[] metaList;
  private MetaSession metaSession;
  private boolean enableAuth;

  private static final String osName;

  static {
    Properties p = System.getProperties();
    osName = p.getProperty("os.name");
    logger.info("operating system name: {}", osName);
  }

  public ClusterManager(ClientOptions opts) throws IllegalArgumentException {
    setTimeout((int) opts.getOperationTimeout().toMillis());
    this.enableCounter = opts.isEnablePerfCounter();
    if (enableCounter) {
      MetricsManager.detectHostAndInit(
          opts.getFalconPerfCounterTags(), (int) opts.getFalconPushInterval().getSeconds());
    }

    replicaSessions = new ConcurrentHashMap<rpc_address, ReplicaSession>();
    replicaGroup = getEventLoopGroupInstance(opts.getAsyncWorkers());
    metaGroup = getEventLoopGroupInstance(1);
    tableGroup = getEventLoopGroupInstance(1);

    metaList = opts.getMetaServers().split(",");
    // the constructor of meta session is depend on the replicaSessions,
    // so the replicaSessions should be initialized earlier
    metaSession =
        new MetaSession(this, metaList, (int) opts.getMetaQueryTimeout().toMillis(), 10, metaGroup);

    this.enableAuth = opts.isEnableAuth();
  }

  public EventExecutor getExecutor() {
    return tableGroup.next();
  }

  public MetaSession getMetaSession() {
    return metaSession;
  }

  public ReplicaSession getReplicaSession(rpc_address address) {
    if (address.isInvalid()) {
      return null;
    }
    ReplicaSession ss = replicaSessions.get(address);
    if (ss != null) return ss;
    synchronized (this) {
      ss = replicaSessions.get(address);
      if (ss != null) return ss;
      ss =
          new ReplicaSession(
              address,
              replicaGroup,
              max(operationTimeout, ClientOptions.MIN_SOCK_CONNECT_TIMEOUT),
              enableAuth);
      replicaSessions.put(address, ss);
      return ss;
    }
  }

  public int getTimeout() {
    return operationTimeout;
  }

  public long getRetryDelay(long timeoutMs) {
    return (timeoutMs < 3 ? 1 : timeoutMs / 3);
  }

  public int getRetryDelay() {
    return retryDelay;
  }

  public boolean counterEnabled() {
    return enableCounter;
  }

  public void setTimeout(int t) {
    operationTimeout = t;
    // set retry delay as t/3.
    retryDelay = (t < 3 ? 1 : t / 3);
  }

  public static EventLoopGroup getEventLoopGroupInstance(int threadsCount) {
    logger.debug("create nio eventloop group");
    return new NioEventLoopGroup(threadsCount);
  }

  public static Class getSocketChannelClass() {
    logger.debug("create nio eventloop group");
    return NioSocketChannel.class;
  }

  @Override
  public String[] getMetaList() {
    return metaList;
  }

  @Override
  public TableHandler openTable(String name, InternalTableOptions internalTableOptions)
      throws ReplicationException {
    return new TableHandler(this, name, internalTableOptions);
  }

  @Override
  public void close() {
    if (enableCounter) {
      MetricsManager.finish();
    }

    metaSession.closeSession();
    for (Map.Entry<rpc_address, ReplicaSession> entry : replicaSessions.entrySet()) {
      entry.getValue().closeSession();
    }

    Future metaGroupFuture = metaGroup.shutdownGracefully();
    Future replicaGroupFuture = replicaGroup.shutdownGracefully();
    Future tableGroupFuture = tableGroup.shutdownGracefully();

    try {
      metaGroupFuture.sync();
      logger.info("meta group has closed");
    } catch (Exception ex) {
      logger.warn("close meta group failed: ", ex);
    }

    try {
      replicaGroupFuture.sync();
      logger.info("replica group has closed");
    } catch (Exception ex) {
      logger.warn("close replica group failed: ", ex);
    }

    try {
      tableGroupFuture.sync();
      logger.info("table group has closed");
    } catch (Exception ex) {
      logger.warn("close table group failed: ", ex);
    }

    logger.info("cluster manager has closed");
  }
}

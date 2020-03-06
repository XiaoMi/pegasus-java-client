// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.rpc.async;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.base.gpid;
import com.xiaomi.infra.pegasus.base.rpc_address;
import com.xiaomi.infra.pegasus.client.FutureGroup;
import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.operator.client_operator;
import com.xiaomi.infra.pegasus.operator.query_cfg_operator;
import com.xiaomi.infra.pegasus.replication.partition_configuration;
import com.xiaomi.infra.pegasus.replication.query_cfg_request;
import com.xiaomi.infra.pegasus.replication.query_cfg_response;
import com.xiaomi.infra.pegasus.rpc.KeyHasher;
import com.xiaomi.infra.pegasus.rpc.ReplicationException;
import com.xiaomi.infra.pegasus.rpc.Table;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.EventExecutor;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;

/** Created by sunweijie@xiaomi.com on 16-11-11. */
public class TableHandler extends Table {
  public static final class ReplicaConfiguration {
    public gpid pid = new gpid();
    public long ballot = 0;
    public rpc_address primaryAddress = new rpc_address();
    public ReplicaSession primarySession = null;
    public List<ReplicaSession> secondarySessions = new ArrayList<>();
  }

  static final class TableConfiguration {
    ArrayList<ReplicaConfiguration> replicas;
    long updateVersion;
  }

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TableHandler.class);
  ClusterManager manager_;
  EventExecutor executor_; // should be only one thread in this service

  AtomicReference<TableConfiguration> tableConfig_;
  AtomicBoolean inQuerying_;
  long lastQueryTime_;
  int backupRequestDelayMs;

  public TableHandler(ClusterManager mgr, String name, KeyHasher h, int backupRequestDelayMs)
      throws ReplicationException {
    int i = 0;
    for (; i < name.length(); i++) {
      char c = name.charAt(i);
      if ((c >= 'a' && c <= 'z')
          || (c >= 'A' && c <= 'Z')
          || (c >= '0' && c <= '9')
          || c == '_'
          || c == '.'
          || c == ':') continue;
      else break;
    }
    if (name.length() > 0 && i == name.length()) {
      logger.info(
          "initialize table handler, table name is \"{}\"", StringEscapeUtils.escapeJava(name));
    } else {
      logger.warn(
          "initialize table handler, maybe invalid table name \"{}\"",
          StringEscapeUtils.escapeJava(name));
    }

    query_cfg_request req = new query_cfg_request(name, new ArrayList<Integer>());
    query_cfg_operator op = new query_cfg_operator(new gpid(-1, -1), req);
    mgr.getMetaSession().query(op, 5);
    error_types err = MetaSession.getMetaServiceError(op);
    if (err != error_types.ERR_OK) {
      handleMetaException(err, mgr, name);
      return;
    }
    query_cfg_response resp = op.get_response();
    logger.info(
        "query meta configuration succeed, table_name({}), app_id({}), partition_count({})",
        name,
        resp.app_id,
        resp.partition_count);

    // superclass members
    tableName_ = name;
    appID_ = resp.app_id;
    hasher_ = h;

    // members of this
    manager_ = mgr;
    executor_ = manager_.getExecutor(name, 1);
    this.backupRequestDelayMs = backupRequestDelayMs;

    tableConfig_ = new AtomicReference<TableConfiguration>(null);
    initTableConfiguration(resp);

    inQuerying_ = new AtomicBoolean(false);
    lastQueryTime_ = 0;
  }

  public ReplicaConfiguration getReplicaConfig(int index) {
    return tableConfig_.get().replicas.get(index);
  }

  // update the table configuration & appID_ according to to queried response
  // there should only be one thread to do the table config update
  void initTableConfiguration(query_cfg_response resp) {
    TableConfiguration oldConfig = tableConfig_.get();

    TableConfiguration newConfig = new TableConfiguration();
    newConfig.updateVersion = (oldConfig == null) ? 1 : (oldConfig.updateVersion + 1);
    newConfig.replicas = new ArrayList<>(resp.getPartition_count());
    for (int i = 0; i != resp.getPartition_count(); ++i) {
      ReplicaConfiguration newReplicaConfig = new ReplicaConfiguration();
      newReplicaConfig.pid.set_app_id(resp.getApp_id());
      newReplicaConfig.pid.set_pidx(i);
      newConfig.replicas.add(newReplicaConfig);
    }

    // create sessions for primary and secondaries
    FutureGroup<Void> futureGroup = new FutureGroup<>(resp.getPartition_count());
    for (partition_configuration pc : resp.getPartitions()) {
      ReplicaConfiguration s = newConfig.replicas.get(pc.getPid().get_pidx());
      s.ballot = pc.ballot;

      // create primary sessions
      s.primaryAddress = pc.primary;
      if (!pc.primary.isInvalid()) {
        s.primarySession = manager_.getReplicaSession(pc.primary);
        ChannelFuture fut = s.primarySession.tryConnect();
        if (fut != null) {
          futureGroup.add(fut);
        }

        // backup request is enabled, get all secondary sessions
        s.secondarySessions.clear();
        if (isEnableBackupRequest()) {
          // secondary sessions
          pc.secondaries.forEach(
              secondary -> {
                if (!secondary.isInvalid()) {
                  ReplicaSession session = manager_.getReplicaSession(secondary);
                  s.secondarySessions.add(session);
                  ChannelFuture channelFuture = session.tryConnect();
                  if (channelFuture != null) {
                    futureGroup.add(channelFuture);
                  }
                }
              });
        }
      }
    }

    // Warm up the connections during client.openTable, so RPCs thereafter can
    // skip the connect process.
    try {
      futureGroup.waitAllCompleteOrOneFail(manager_.getTimeout());
    } catch (PException e) {
      logger.warn("failed to connect with some replica servers: ", e);
    }

    // there should only be one thread to do the table config update
    appID_ = resp.getApp_id();
    tableConfig_.set(newConfig);
  }

  void onUpdateConfiguration(final query_cfg_operator op) {
    error_types err = MetaSession.getMetaServiceError(op);
    if (err != error_types.ERR_OK) {
      logger.warn("query meta for table({}) failed, error_code({})", tableName_, err.toString());
    } else {
      logger.info("query meta for table({}) received response", tableName_);
      query_cfg_response resp = op.get_response();
      if (resp.app_id != appID_ || resp.partition_count != tableConfig_.get().replicas.size()) {
        logger.warn(
            "table({}) meta reset, app_id({}->{}), partition_count({}->{})",
            tableName_,
            appID_,
            resp.app_id,
            tableConfig_.get().replicas.size(),
            resp.partition_count);
      }
      initTableConfiguration(resp);
    }

    inQuerying_.set(false);
  }

  boolean tryQueryMeta(long cachedConfigVersion) {
    if (!inQuerying_.compareAndSet(false, true)) return false;

    long now = System.currentTimeMillis();
    if (now - lastQueryTime_ < manager_.getRetryDelay()) {
      inQuerying_.set(false);
      return false;
    }
    if (tableConfig_.get().updateVersion > cachedConfigVersion) {
      inQuerying_.set(false);
      return false;
    }

    lastQueryTime_ = now;
    query_cfg_request req = new query_cfg_request(tableName_, new ArrayList<Integer>());
    final query_cfg_operator query_op = new query_cfg_operator(new gpid(-1, -1), req);

    logger.info("query meta for table({}) query request", tableName_);
    manager_
        .getMetaSession()
        .asyncQuery(
            query_op,
            new Runnable() {
              @Override
              public void run() {
                onUpdateConfiguration(query_op);
              }
            },
            5);

    return true;
  }

  void onRpcReply(
      ClientRequestRound round,
      int tryId,
      ReplicaConfiguration cachedHandle,
      long cachedConfigVersion) {
    // judge if it is the first response
    if (round.isSuccess) {
      return;
    } else {
      synchronized (round) {
        // the correct response has been received
        if (round.isSuccess) {
          return;
        }
        round.isSuccess = true;
      }
    }

    // cancel the backup request task
    if (round.backupRequstTask != null) {
      round.backupRequstTask.cancel(true);
    }

    client_operator operator = round.getOperator();
    boolean needQueryMeta = false;
    switch (operator.rpc_error.errno) {
      case ERR_OK:
        round.thisRoundCompletion();
        return;

        // timeout
      case ERR_TIMEOUT: // <- operation timeout
        logger.warn(
            "{}: replica server({}) rpc timeout for gpid({}), operator({}), try({}), error_code({}), not retry",
            tableName_,
            cachedHandle.primarySession.name(),
            operator.get_gpid().toString(),
            operator,
            tryId,
            operator.rpc_error.errno.toString());
        break;

        // under these cases we should query the new config from meta and retry later
      case ERR_SESSION_RESET: // <- connection with the server failed
      case ERR_OBJECT_NOT_FOUND: // <- replica server doesn't serve this gpid
      case ERR_INVALID_STATE: // <- replica server is not primary
        logger.warn(
            "{}: replica server({}) doesn't serve gpid({}), operator({}), try({}), error_code({}), need query meta",
            tableName_,
            cachedHandle.primarySession.name(),
            operator.get_gpid().toString(),
            operator,
            tryId,
            operator.rpc_error.errno.toString());
        needQueryMeta = true;
        break;

        // under these cases we should retry later without querying the new config from meta
      case ERR_NOT_ENOUGH_MEMBER:
      case ERR_CAPACITY_EXCEEDED:
        logger.warn(
            "{}: replica server({}) can't serve writing for gpid({}), operator({}), try({}), error_code({}), retry later",
            tableName_,
            cachedHandle.primarySession.name(),
            operator.get_gpid().toString(),
            operator,
            tryId,
            operator.rpc_error.errno.toString());
        break;

        // under other cases we should not retry
      default:
        logger.error(
            "{}: replica server({}) fails for gpid({}), operator({}), try({}), error_code({}), not retry",
            tableName_,
            cachedHandle.primarySession.name(),
            operator.get_gpid().toString(),
            operator,
            tryId,
            operator.rpc_error.errno.toString());
        round.thisRoundCompletion();
        return;
    }

    if (needQueryMeta) {
      tryQueryMeta(cachedConfigVersion);
    }

    // must use new round here, because round.isSuccess is true now
    tryDelayCall(
        new ClientRequestRound(
            round.operator, round.callback, round.enableCounter, round.timeoutMs),
        tryId + 1);
  }

  void tryDelayCall(final ClientRequestRound round, final int tryId) {
    long nanoDelay = manager_.getRetryDelay(round.timeoutMs) * 1000000L;
    if (round.expireNanoTime - System.nanoTime() > nanoDelay) {
      executor_.schedule(
          new Runnable() {
            @Override
            public void run() {
              call(round, tryId);
            }
          },
          nanoDelay,
          TimeUnit.NANOSECONDS);
    } else {
      // errno == ERR_UNKNOWN means the request has never attemptted to contact any replica servers
      // this may happen when we can't initialize a null replica session for a long time
      if (round.getOperator().rpc_error.errno == error_types.ERR_UNKNOWN) {
        round.getOperator().rpc_error.errno = error_types.ERR_TIMEOUT;
      }
      round.thisRoundCompletion();
    }
  }

  void call(final ClientRequestRound round, final int tryId) {
    // tableConfig & handle is initialized in constructor, so both shouldn't be null
    final TableConfiguration tableConfig = tableConfig_.get();
    final ReplicaConfiguration handle =
        tableConfig.replicas.get(round.getOperator().get_gpid().get_pidx());

    // send request to primary session
    if (handle.primarySession != null) {
      // if it's not write operation and backup request is enabled, schedule to send to secondaries
      if (!round.operator.isWrite && isEnableBackupRequest()) {
        round.backupRequstTask =
            executor_.schedule(
                new Runnable() {
                  @Override
                  public void run() {
                    for (ReplicaSession session : handle.secondarySessions) {
                      session.asyncSend(
                          round.getOperator(),
                          new Runnable() {
                            @Override
                            public void run() {
                              onRpcReply(round, tryId, handle, tableConfig.updateVersion);
                            }
                          },
                          round.timeoutMs,
                          true);
                    }
                  }
                },
                backupRequestDelayMs,
                TimeUnit.MILLISECONDS);
      }

      handle.primarySession.asyncSend(
          round.getOperator(),
          new Runnable() {
            @Override
            public void run() {
              onRpcReply(round, tryId, handle, tableConfig.updateVersion);
            }
          },
          round.timeoutMs,
          false);
    } else {
      logger.warn(
          "{}: no primary for gpid({}), operator({}), try({}), retry later",
          tableName_,
          round.getOperator().get_gpid().toString(),
          round.getOperator(),
          tryId);
      tryQueryMeta(tableConfig.updateVersion);
      tryDelayCall(round, tryId + 1);
    }
  }

  @Override
  public int getPartitionCount() {
    return tableConfig_.get().replicas.size();
  }

  @Override
  public void operate(client_operator op, int timeoutMs) throws ReplicationException {
    final FutureTask<Void> syncer =
        new FutureTask<Void>(
            new Callable<Void>() {
              @Override
              public Void call() throws Exception {
                return null;
              }
            });
    ClientOPCallback cb =
        new ClientOPCallback() {
          @Override
          public void onCompletion(client_operator op) throws Throwable {
            syncer.run();
          }
        };

    asyncOperate(op, cb, timeoutMs);

    try {
      syncer.get(timeoutMs, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      logger.info("got exception: " + e);
      throw new ReplicationException(e);
    } catch (ExecutionException e) {
      logger.info("got exception: " + e);
      throw new ReplicationException(e);
    } catch (TimeoutException e) {
      op.rpc_error.errno = error_types.ERR_TIMEOUT;
    }

    if (op.rpc_error.errno != error_types.ERR_OK) {
      throw new ReplicationException(op.rpc_error.errno);
    }
  }

  @Override
  public EventExecutor getExecutor() {
    return executor_;
  }

  @Override
  public int getDefaultTimeout() {
    return manager_.getTimeout();
  }

  @Override
  public void asyncOperate(client_operator op, ClientOPCallback callback, int timeoutMs) {
    if (timeoutMs <= 0) {
      timeoutMs = manager_.getTimeout();
    }

    ClientRequestRound round =
        new ClientRequestRound(op, callback, manager_.counterEnabled(), (long) timeoutMs);
    call(round, 1);
  }

  private void handleMetaException(error_types err_type, ClusterManager mgr, String name)
      throws ReplicationException {
    String metaServer = Arrays.toString(mgr.getMetaList());
    String message = "";
    String header = "[metaServer=" + metaServer + ",tableName=" + name + "]";
    switch (err_type) {
      case ERR_OBJECT_NOT_FOUND:
        message =
            " No such table. Please make sure your meta addresses and table name are correct!";
        break;
      case ERR_BUSY_CREATING:
        message = " The table is creating, please wait a moment and retry it!";
        break;
      case ERR_BUSY_DROPPING:
        message = " The table is dropping, please confirm the table name!";
        break;
      case ERR_SESSION_RESET:
        message = " Unable to connect to the meta servers!";
    }
    throw new ReplicationException(err_type, header + message);
  }

  private boolean isEnableBackupRequest() {
    return backupRequestDelayMs > 0;
  }
}

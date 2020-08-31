package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.ReplicaSession;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler.ReplicaConfiguration;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler.TableConfiguration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BackupRequestInterceptor implements TableInterceptor {

  private boolean isOpen;

  public BackupRequestInterceptor(boolean isOpen) {
    this.isOpen = isOpen;
  }

  @Override
  public void interceptBefore(ClientRequestRound clientRequestRound, TableHandler tableHandler) {
    backupCall(clientRequestRound, tableHandler);
  }

  @Override
  public void interceptAfter(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler) {
    // cancel the backup request task
    if (clientRequestRound.backupRequestTask != null) {
      clientRequestRound.backupRequestTask.cancel(true);
    }
  }

  private void backupCall(ClientRequestRound clientRequestRound, TableHandler tableHandler) {
    if (!isOpen || !clientRequestRound.getOperator().enableBackupRequest) {
      return;
    }

    final TableConfiguration tableConfig = tableHandler.tableConfig_.get();
    final ReplicaConfiguration handle =
        tableConfig.replicas.get(clientRequestRound.getOperator().get_gpid().get_pidx());

    clientRequestRound.backupRequestTask =
        tableHandler.executor_.schedule(
            () -> {
              // pick a secondary at random
              ReplicaSession secondarySession =
                  handle.secondarySessions.get(
                      new Random().nextInt(handle.secondarySessions.size()));
              secondarySession.asyncSend(
                  clientRequestRound.getOperator(),
                  () ->
                      tableHandler.onRpcReply(
                          clientRequestRound, tableConfig.updateVersion, secondarySession.name()),
                  clientRequestRound.timeoutMs,
                  true);
            },
            tableHandler.backupRequestDelayMs,
            TimeUnit.MILLISECONDS);
  }
}

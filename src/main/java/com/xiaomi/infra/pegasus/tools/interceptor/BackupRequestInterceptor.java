package com.xiaomi.infra.pegasus.tools.interceptor;

import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.ReplicaSession;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler.ReplicaConfiguration;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler.TableConfiguration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BackupRequestInterceptor implements TableInterceptor {
  private boolean isEnable;

  private TableHandler tableHandler;
  private ClientRequestRound clientRequestRound;

  public BackupRequestInterceptor(
      boolean isEnable, TableHandler tableHandler, ClientRequestRound clientRequestRound) {
    this.isEnable = isEnable;
    this.tableHandler = tableHandler;
    this.clientRequestRound = clientRequestRound;
  }

  @Override
  public void interceptBefore() throws Exception {
    backupCall();
  }

  @Override
  public void interceptAfter() throws Exception {}

  @Override
  public boolean isEnable() {
    return isEnable;
  }

  private void backupCall() {
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
                  new Runnable() {
                    @Override
                    public void run() {
                      tableHandler.onRpcReply(
                          clientRequestRound, tableConfig.updateVersion, secondarySession.name());
                    }
                  },
                  clientRequestRound.timeoutMs,
                  true);
            },
            tableHandler.backupRequestDelayMs,
            TimeUnit.MILLISECONDS);
  }
}

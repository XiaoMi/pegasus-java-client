package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;

public class AutoRetryInterceptor implements TableInterceptor {
  private long retryTime;
  private long timeout;

  public AutoRetryInterceptor(long retryTime, long timeout) {
    this.retryTime = retryTime;
    this.timeout = timeout;
  }

  @Override
  public void before(ClientRequestRound clientRequestRound, TableHandler tableHandler) {
    clientRequestRound.timeoutMs = retryTime;
  }

  @Override
  // TODO(jiashuo1) open AutoRetry and backup-request at same time will result in thread-safe
  // problem on updating `clientRequestRound.timeoutMs`
  public void after(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler) {
    if (errno != error_types.ERR_TIMEOUT) {
      return;
    }

    clientRequestRound.remainingTime -= timeout;
    if (clientRequestRound.remainingTime <= 0) {
      return;
    }

    clientRequestRound.timeoutMs =
        clientRequestRound.remainingTime < this.retryTime
            ? clientRequestRound.remainingTime
            : this.retryTime;

    tableHandler.call(clientRequestRound);
  }
}

package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;

public class AutoRetryInterceptor implements TableInterceptor {
  private final long retryTimeMs;

  public AutoRetryInterceptor(long retryTime) {
    this.retryTimeMs = retryTime;
  }

  @Override
  public void before(ClientRequestRound clientRequestRound, TableHandler tableHandler) {
    clientRequestRound.timeoutMs =
        clientRequestRound.remainingTime < retryTimeMs
            ? clientRequestRound.remainingTime
            : retryTimeMs;
  }

  @Override
  public void after(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler) {
    if (errno != error_types.ERR_TIMEOUT) {
      return;
    }

    clientRequestRound.remainingTime -= clientRequestRound.timeoutMs;
    if (clientRequestRound.remainingTime <= 0) {
      return;
    }

    tableHandler.call(clientRequestRound);
  }
}

package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.rpc.TableOptions.RetryOptions;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;
import org.slf4j.Logger;

public class AutoRetryInterceptor implements TableInterceptor {
  private static final Logger logger =
      org.slf4j.LoggerFactory.getLogger(AutoRetryInterceptor.class);
  private RetryOptions retryOptions;

  /**
   * the interceptor support the rpc retry, which will guarantee return in request timeout user
   * passed.
   *
   * @param retryOptions how to retry after call failed, detail see {@link RetryOptions}
   */
  public AutoRetryInterceptor(RetryOptions retryOptions) {
    this.retryOptions = retryOptions;
  }

  @Override
  public void before(ClientRequestRound clientRequestRound, TableHandler tableHandler) {
    // if the remainingTime <= retryTimeMs + delayTimeMs, means the remainingTime only can be enough
    // to call one time, so set the timeout = remainingTime, it's especially note that if
    // remainingTime <= retryTimeMs + delayTimeMs at first call, the rpc will be only called once
    // and not trigger retry logic
    clientRequestRound.timeoutMs =
        clientRequestRound.remainingTime <= retryOptions.retryTimeMs() + retryOptions.delayTimeMs()
            ? clientRequestRound.remainingTime
            : retryOptions.retryTimeMs();
  }

  @Override
  public void after(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler) {
    if (errno != error_types.ERR_TIMEOUT) {
      return;
    }

    clientRequestRound.remainingTime -= (clientRequestRound.timeoutMs + retryOptions.delayTimeMs());

    if (clientRequestRound.remainingTime <= 0) {
      return;
    }

    try {
      Thread.sleep(retryOptions.delayTimeMs());
    } catch (InterruptedException e) {
      logger.warn(
          "sleep {} is interrupted when ready for retrying call, which will start next call rpc immediately");
    }

    tableHandler.call(clientRequestRound);
  }
}

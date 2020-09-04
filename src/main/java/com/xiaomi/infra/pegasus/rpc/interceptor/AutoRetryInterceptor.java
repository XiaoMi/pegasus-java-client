package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.rpc.TableOptions.RetryOptions;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;

public class AutoRetryInterceptor implements TableInterceptor {
  private static final Logger logger =
      org.slf4j.LoggerFactory.getLogger(AutoRetryInterceptor.class);
  private long retryTimeMs;
  private long delayTimeMs;
  private int maxRetryTime;

  /**
   * the interceptor support the rpc retry, which will guarantee return in request timeout user
   * passed.
   *
   * @param retryOptions how to retry after call failed, detail see {@link RetryOptions}
   */
  public AutoRetryInterceptor(RetryOptions retryOptions) {
    this.retryTimeMs = retryOptions.retryTimeMs();
    this.delayTimeMs = retryOptions.delayTimeMs();
    this.maxRetryTime = retryOptions.maxRetryTime();
  }

  @Override
  public void before(ClientRequestRound clientRequestRound, TableHandler tableHandler) {
    // if the remainingTime <= nextCallTime or clientRequestRound.tryId = maxRetryTime, means the
    // remainingTime only can be enough to call one time, so set the timeout = remainingTime, it's
    // especially note that if the case happen at first call, the rpc will be only called once and
    // not trigger retry logic
    clientRequestRound.nextDelayTime =
        getExpDelayTimeMs(clientRequestRound.tryId, clientRequestRound.remainingTime);
    long nextCallTime = retryTimeMs + clientRequestRound.nextDelayTime;
    clientRequestRound.timeoutMs =
        (clientRequestRound.remainingTime <= nextCallTime)
                || (clientRequestRound.tryId == maxRetryTime)
            ? clientRequestRound.remainingTime
            : retryTimeMs;
  }

  @Override
  public void after(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler) {
    if (errno != error_types.ERR_TIMEOUT) {
      return;
    }

    if (++clientRequestRound.tryId > maxRetryTime) {
      return;
    }

    clientRequestRound.remainingTime -=
        (clientRequestRound.timeoutMs + clientRequestRound.nextDelayTime);
    if (clientRequestRound.remainingTime <= 0) {
      return;
    }

    try {
      Thread.sleep(clientRequestRound.nextDelayTime);
    } catch (InterruptedException e) {
      logger.warn(
          "sleep {}ms is interrupted when ready for retrying call, which will start next call rpc immediately",
          clientRequestRound.nextDelayTime);
    }

    tableHandler.call(clientRequestRound);
  }

  /**
   * compute the next delay time before new rpc call
   *
   * @param tryCount the count of rpc call
   * @param remainingTime the remaining time of request time
   * @return the delay time base `Exponential Backoff` before new rpc call, if the remainingTime <
   *     delayTimeMs * 2 ^ (tryCount - 1), return remainingTime
   */
  private long getExpDelayTimeMs(int tryCount, long remainingTime) {
    return RandomUtils.nextLong(0, Math.min(remainingTime, delayTimeMs * 2 ^ (tryCount - 1)));
  }
}

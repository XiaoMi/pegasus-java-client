package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.operator.client_operator;
import com.xiaomi.infra.pegasus.rpc.Table.ClientOPCallback;
import com.xiaomi.infra.pegasus.rpc.TableOptions;
import com.xiaomi.infra.pegasus.rpc.TableOptions.RetryOptions;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;

/**
 * the interceptor support the rpc retry based `Exponential Backoff`, which will guarantee return in
 * request timeout user passed.
 *
 * <p>every rpc call has timeout{@link ClientRequestRound#timeoutMs}, if not set {@link
 * RetryOptions} when open table using {@link TableOptions}, the rpc timeout is equal with request
 * timeout, see the {@link TableHandler#asyncOperate(client_operator, ClientOPCallback, int)},
 * otherwise, the timeout is updated by the class and is equal with {@link
 * RetryOptions#tryTimeoutMs}, or is equal with {@link ClientRequestRound#remainingTime} at last
 * time, detail see {@link AutoRetryInterceptor#updateRequestTimeout(ClientRequestRound)}
 */
public class AutoRetryInterceptor implements TableInterceptor {
  private static final Logger logger =
      org.slf4j.LoggerFactory.getLogger(AutoRetryInterceptor.class);
  private long tryTimeoutMs;
  private long delayTimeMs;
  private int maxRetryCount;

  public AutoRetryInterceptor(RetryOptions retryOptions) {
    this.tryTimeoutMs = retryOptions.tryTimeoutMs();
    this.delayTimeMs = retryOptions.delayTimeMs();
    this.maxRetryCount = retryOptions.maxTryCount();
  }

  @Override
  public void before(ClientRequestRound clientRequestRound, TableHandler tableHandler) {
    updateRequestTimeout(clientRequestRound);
  }

  /**
   * This method used for updating request timeout value before call
   *
   * <p>the rpc timeout is equal with tryTimeout in general, but if the remainingTime <=
   * nextCallTime or clientRequestRound.tryId = maxTryCount, means the remainingTime only can be
   * enough to call one time, so set the rpc timeout = remainingTime, it's especially note that if
   * the case happen at first call, the rpc will be only called once and not trigger retry logic.
   *
   * <p>case 1: the last remaining time is just enough for once, the last rpc timeout=tryTimeout
   * |--------------------------request timeout------------------------------------------|
   * |---tryTimeout(1)---|-delayTime-|---tryTimeout(2)---|-delayTime-|---tryTimeout(3)---|
   *
   * <p>case 2: the last remaining time is not enough for once, the last rpc timeout=remainingTime
   * |-------------------------request timeout----------------------------------------------|
   * |----tryTimeout(1)----|-delayTime-|----tryTimeout(2)----|-delayTime-|-remainingTime(3)-|
   *
   * <p>case 3: the try count is equal with maxTryTime=2, the last rpc timeout=remainingTime
   * |-----------------------request timeout------------------------------------------------|
   * |----tryTimeout(1)----|-delayTime-|------------------remainingTime(2)------------------|
   *
   * <p>case 4: the tryTimeout + delayTime > request timeout(also remainingTime) at first call, the
   * rpc timeout=request timeout and rpc won't be retried
   *
   * <p>|-----------------------request timeout----------------------------------|
   * </>|---------------------tryTimeout(1)----------------|---------delayTime-----------|
   *
   * @param clientRequestRound request body
   */
  private void updateRequestTimeout(ClientRequestRound clientRequestRound) {
    clientRequestRound.nextDelayTime =
        getExpDelayTimeMs(clientRequestRound.tryId, clientRequestRound.remainingTime);
    long nextCallTime = tryTimeoutMs + clientRequestRound.nextDelayTime;

    clientRequestRound.timeoutMs =
        (clientRequestRound.remainingTime <= nextCallTime)
                || (clientRequestRound.tryId == maxRetryCount)
            ? clientRequestRound.remainingTime
            : tryTimeoutMs;

    clientRequestRound.remainingTime -=
        (clientRequestRound.timeoutMs + clientRequestRound.nextDelayTime);
  }

  @Override
  public void after(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler) {
    retryCall(clientRequestRound, errno, tableHandler);
  }

  /**
   * sleep the delayTime and retry call
   *
   * @param clientRequestRound clientRequestRound
   * @param errno rpc call response error code
   * @param tableHandler tableHandler
   */
  private void retryCall(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler) {
    if (errno != error_types.ERR_TIMEOUT) {
      return;
    }

    if (++clientRequestRound.tryId > maxRetryCount || clientRequestRound.remainingTime <= 0) {
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
   * compute the next delay time based `Exponential Backoff` before new rpc call
   *
   * @param tryCount the count of rpc call
   * @param remainingTime the remaining time of request time
   * @return the delay time base `Exponential Backoff` before new rpc call, if the remainingTime <
   *     delayTimeMs * 2 ^ (maxTryCount - 1), return remainingTime
   */
  private long getExpDelayTimeMs(int tryCount, long remainingTime) {
    return RandomUtils.nextLong(0, Math.min(remainingTime, delayTimeMs * 2 ^ (tryCount - 1)));
  }
}

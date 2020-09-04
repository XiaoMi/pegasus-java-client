// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;

/** TableOptions is for opening a Pegasus table with some feature */
public class TableOptions {

  /**
   * this class control the retry options based `Exponential Backoff` after rpc call failed, used by
   * {@link com.xiaomi.infra.pegasus.rpc.interceptor.AutoRetryInterceptor}
   */
  public static class RetryOptions {
    private long tryTimeoutMs;
    private long delayTimeMs;
    private int MaxTryCount;

    public RetryOptions(long tryTimeoutMs, long delayTimeMs) {
      assert (tryTimeoutMs > 0 && delayTimeMs >= 0);
      this.tryTimeoutMs = tryTimeoutMs;
      this.delayTimeMs = delayTimeMs;
      this.MaxTryCount = 3;
    }

    /**
     * set the try timeout
     *
     * @param tryTimeoutMs try timeout, it should be greater than zero. if set RetryOptions for
     *     {@link TableOptions}, which means the rpc timeout is updated to tryTimeout, detail see
     *     {@link
     *     com.xiaomi.infra.pegasus.rpc.interceptor.AutoRetryInterceptor#updateRequestTimeout(ClientRequestRound)}
     * @return this
     */
    public RetryOptions withTryTimeoutMs(long tryTimeoutMs) {
      this.tryTimeoutMs = tryTimeoutMs;
      return this;
    }

    /**
     * set base delay time before the new rpc call, the actual delay time base `Exponential
     * Backoff`, detail see {@link
     * com.xiaomi.infra.pegasus.rpc.interceptor.AutoRetryInterceptor#getExpDelayTimeMs(int, long)}
     *
     * @param delayTimeMs base delay time before new rpc call
     * @return this
     */
    public RetryOptions withDelayTimeMs(long delayTimeMs) {
      this.delayTimeMs = delayTimeMs;
      return this;
    }

    /**
     * set max try call count
     *
     * @param maxTryCount max try call count, default is 3
     * @return this
     */
    public RetryOptions withMaxTryCount(int maxTryCount) {
      this.MaxTryCount = maxTryCount;
      return this;
    }

    public long tryTimeoutMs() {
      return tryTimeoutMs;
    }

    public long delayTimeMs() {
      return delayTimeMs;
    }

    public int maxTryCount() {
      return MaxTryCount;
    }
  }

  private int backupRequestDelayMs;
  private boolean enableCompression;
  private RetryOptions retryOptions;

  public TableOptions() {
    this.backupRequestDelayMs = 0;
    this.enableCompression = false;
  }

  public TableOptions withBackupRequestDelayMs(int backupRequestDelayMs) {
    this.backupRequestDelayMs = backupRequestDelayMs;
    return this;
  }

  public TableOptions withCompression(boolean enableCompression) {
    this.enableCompression = enableCompression;
    return this;
  }

  public TableOptions withRetry(RetryOptions retryOptions) {
    this.retryOptions = retryOptions;
    return this;
  }

  public int backupRequestDelayMs() {
    return this.backupRequestDelayMs;
  }

  public boolean enableBackupRequest() {
    return backupRequestDelayMs > 0;
  }

  public boolean enableCompression() {
    return enableCompression;
  }

  public boolean enableAutoRetry() {
    return retryOptions != null;
  }

  public RetryOptions retryOptions() {
    return retryOptions;
  }
}

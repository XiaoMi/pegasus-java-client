// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.rpc;

import com.xiaomi.infra.pegasus.client.PegasusClient.PegasusHasher;
import com.xiaomi.infra.pegasus.operator.client_operator;
import com.xiaomi.infra.pegasus.rpc.Table.ClientOPCallback;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;
import com.xiaomi.infra.pegasus.rpc.interceptor.AutoRetryInterceptor;

/** TableOptions is the internal options for opening a Pegasus table. */
public class TableOptions {

  /**
   * this class control the retry options after rpc call failed based `Exponential Backoff`
   *
   * <p>every rpc call has timeout{@link ClientRequestRound#timeoutMs}, if not set the RetryOptions
   * when open table, the rpc timeout is equal with request timeout, see the {@link
   * TableHandler#asyncOperate(client_operator, ClientOPCallback, int)}, otherwise, the timeout is
   * updated by {@link AutoRetryInterceptor} and is equal with `retryTime`{@link
   * RetryOptions#retryTimeMs}, or is equal with `remainingTime`{@link
   * ClientRequestRound#remainingTime} at last time, detail see {@link
   * AutoRetryInterceptor#before(ClientRequestRound, TableHandler)}
   *
   * <p>after call failed, the retry call will delay `delayTimeMs`{@link RetryOptions#delayTimeMs}
   * and delayTimeMs = random()
   */
  public static class RetryOptions {
    private long retryTimeMs;
    private long delayTimeMs;
    private int maxRetryTime;

    public RetryOptions(long retryTimeMs, long delayTimeMs) {
      assert (retryTimeMs > 0 && delayTimeMs >= 0);
      this.retryTimeMs = retryTimeMs;
      this.delayTimeMs = delayTimeMs;
    }

    /**
     * for example:
     *
     * <p>user hope the request timeout is 1000ms and pass the options: retryTime=200ms, which means
     * it will be timeout after 200ms, if the last call has not enough time, the timeout will be
     * remaining time can guarantee return before 1000ms
     *
     * @param retryTimeMs the rpc timeout if enable rpc retry
     * @return return this
     */
    public RetryOptions withRetryTimeMs(long retryTimeMs) {
      this.retryTimeMs = retryTimeMs;
      return this;
    }

    /**
     * @param delayTimeMs if on
     * @return
     */
    public RetryOptions withDelayTimeMs(long delayTimeMs) {
      this.delayTimeMs = delayTimeMs;
      return this;
    }

    public RetryOptions withMaxRetryTime(int maxRetryTime) {
      this.maxRetryTime = maxRetryTime;
      return this;
    }

    public long retryTimeMs() {
      return retryTimeMs;
    }

    public long delayTimeMs() {
      return delayTimeMs;
    }

    public int maxRetryTime() {
      return maxRetryTime;
    }
  }

  private KeyHasher keyHasher;
  private int backupRequestDelayMs;
  private boolean enableCompression;
  private RetryOptions retryOptions;

  public TableOptions() {
    this.keyHasher = new PegasusHasher();
    this.backupRequestDelayMs = 0;
    this.enableCompression = false;
  }

  public TableOptions withKeyHasher(KeyHasher keyHasher) {
    this.keyHasher = keyHasher;
    return this;
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

  public KeyHasher keyHasher() {
    return this.keyHasher;
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

  public static TableOptions forTest() {
    return new TableOptions().withKeyHasher(KeyHasher.DEFAULT);
  }
}

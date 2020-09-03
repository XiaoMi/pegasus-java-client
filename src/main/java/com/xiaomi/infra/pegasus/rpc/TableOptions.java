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
   * this class control how to retry after rpc call failed
   *
   * <p>every rpc call has timeout{@link ClientRequestRound#timeoutMs}, if not set the RetryOptions,
   * the rpc timeout is equal with request timeout, see the {@link
   * TableHandler#asyncOperate(client_operator, ClientOPCallback, int)}, otherwise, the timeout is
   * updated by {@link AutoRetryInterceptor} and is equal with retryTime, or is equal with {@link
   * ClientRequestRound#remainingTime} at last time, detail see {@link
   * AutoRetryInterceptor#before(ClientRequestRound, TableHandler)}
   *
   * <p>for example: user hope the request timeout is 1000ms and pass the retryTime=200ms and
   * delayTime=100ms, which means the call will be retried at 200+100=300ms, 300+(200+100)=600ms,
   * 600+(200+100)=900 and the last retry timeout is 1000-900=100ms if every call is failed, which
   * can guarantee return before 1000ms
   */
  public static class RetryOptions {
    private long retryTimeMs;
    private long delayTimeMs;

    public RetryOptions(long retryTimeMs, long delayTimeMs) {
      assert (retryTimeMs > 0 && delayTimeMs >= 0);
      this.retryTimeMs = retryTimeMs;
      this.delayTimeMs = delayTimeMs;
    }

    public long retryTimeMs() {
      return retryTimeMs;
    }

    public long delayTimeMs() {
      return delayTimeMs;
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

  public TableOptions withRetryTimeMs(RetryOptions retryOptions) {
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

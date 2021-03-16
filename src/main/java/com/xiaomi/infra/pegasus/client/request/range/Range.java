package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.ScanOptions;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public abstract class Range<Response> {
  protected byte[] hashKey;
  protected int timeout;

  protected ScanOptions scanOptions = new ScanOptions();
  protected byte[] startSortKey;
  protected byte[] stopSortKey;

  protected Range(byte[] hashKey, int timeout) {
    this.hashKey = hashKey;
    this.timeout = timeout;
  }

  protected abstract Response commitAndWait(int maxRangeCount)
      throws PException, InterruptedException, ExecutionException, TimeoutException;

  protected Range<Response> withOptions(ScanOptions scanOptions) {
    this.scanOptions = scanOptions;
    return this;
  }

  protected Range<Response> withStartSortKey(byte[] startSortKey) {
    this.startSortKey = startSortKey;
    return this;
  }

  protected Range<Response> withStopSortKey(byte[] stopSortKey) {
    this.stopSortKey = stopSortKey;
    return this;
  }
}

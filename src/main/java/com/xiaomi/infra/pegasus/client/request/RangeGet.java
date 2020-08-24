package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.MultiGetOptions;

public class RangeGet {
  public byte[] hashKey;
  public byte[] startSortKey;
  public byte[] stopSortKey;
  public int maxFetchCount;
  public int maxFetchSize;

  public MultiGetOptions externOptions = new MultiGetOptions();

  public RangeGet(byte[] hashKey, byte[] startSortKey, byte[] stopSortKey) {
    this(hashKey, startSortKey, stopSortKey, 100, 1000);
  }

  public RangeGet(
      byte[] hashKey,
      byte[] startSortKey,
      byte[] stopSortKey,
      int maxFetchCount,
      int maxFetchSize) {
    assert (hashKey != null && hashKey.length != 0 && hashKey.length < 0xFFFF);
    this.hashKey = hashKey;
    this.startSortKey = startSortKey;
    this.stopSortKey = stopSortKey;
    this.maxFetchCount = maxFetchCount;
    this.maxFetchSize = maxFetchSize;
  }
}

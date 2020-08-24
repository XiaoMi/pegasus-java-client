package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.DelRangeOptions;

public class RangeDelete {

  public byte[] hashKey;
  public byte[] startSortKey;
  public byte[] stopSortKey;

  public DelRangeOptions options = new DelRangeOptions();

  public RangeDelete(byte[] hashKey) {
    this(hashKey, "".getBytes(), "".getBytes());
  }

  public RangeDelete(byte[] hashKey, byte[] startSortKey, byte[] stopSortKey) {
    assert (hashKey != null && hashKey.length > 0);
    this.hashKey = hashKey;
    this.startSortKey = startSortKey;
    this.stopSortKey = stopSortKey;
  }
}

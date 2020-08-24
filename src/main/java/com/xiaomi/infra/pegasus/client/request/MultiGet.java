package com.xiaomi.infra.pegasus.client.request;

import java.util.ArrayList;
import java.util.List;

public class MultiGet {
  public byte[] hashKey;
  public List<byte[]> sortKeys;

  public int maxFetchCount;
  public int maxFetchSize;
  public boolean noValue;

  public MultiGet(byte[] hashKey) {
    this(hashKey, new ArrayList<>(), 100, 1000, false);
  }

  public MultiGet(
      byte[] hashKey, List<byte[]> sortKeys, int maxFetchCount, int maxFetchSize, boolean noValue) {
    assert (hashKey != null && hashKey.length != 0 && hashKey.length < 0xFFFF && sortKeys != null);
    this.hashKey = hashKey;
    this.sortKeys = sortKeys;
    this.maxFetchCount = maxFetchCount;
    this.maxFetchSize = maxFetchSize;
    this.noValue = noValue;
  }

  public void add(byte[] sortKey) {
    sortKeys.add(sortKey);
  }
}

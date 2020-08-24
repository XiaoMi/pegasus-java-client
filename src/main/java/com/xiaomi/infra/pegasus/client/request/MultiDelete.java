package com.xiaomi.infra.pegasus.client.request;

import java.util.ArrayList;
import java.util.List;

public class MultiDelete {
  public byte[] hashKey;
  public List<byte[]> sortKeys = new ArrayList<>();

  public MultiDelete(byte[] hashKey) {
    assert (hashKey != null && hashKey.length != 0 && hashKey.length < 0xFFFF);
    this.hashKey = hashKey;
  }

  public MultiDelete(byte[] hashKey, List<byte[]> sortKeys) {
    this.hashKey = hashKey;
    this.sortKeys = sortKeys;
  }

  public void add(byte[] sortKey) {
    sortKeys.add(sortKey);
  }
}

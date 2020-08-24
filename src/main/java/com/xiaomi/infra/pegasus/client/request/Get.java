package com.xiaomi.infra.pegasus.client.request;

public class Get extends Key {

  public Get(byte[] hashKey) {
    super(hashKey);
  }

  public Get(byte[] hashKey, byte[] sortKey) {
    super(hashKey, sortKey);
  }
}

package com.xiaomi.infra.pegasus.client.request;

public class Increment extends Key {
  public int value;
  public int ttlSeconds;

  public Increment(byte[] hashKey, byte[] sortKey) {
    this(hashKey, sortKey, 1, 0);
  }

  public Increment(byte[] hashKey, byte[] sortKey, int value, int ttlSeconds) {
    super(hashKey, sortKey);
    assert (ttlSeconds > -1);
    this.ttlSeconds = ttlSeconds;
    this.value = value;
  }
}

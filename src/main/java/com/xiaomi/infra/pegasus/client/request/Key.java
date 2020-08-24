package com.xiaomi.infra.pegasus.client.request;

class Key {
  public byte[] hashKey = null;
  public byte[] sortKey = null;

  Key(byte[] hashKey) {
    this.hashKey = hashKey;
  }

  Key(byte[] hashKey, byte[] sortKey) {
    this.hashKey = hashKey;
    this.sortKey = sortKey;
  }
}

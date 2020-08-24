// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import java.io.Serializable;

public class Set implements Serializable {
  public byte[] hashKey;
  public byte[] sortKey;
  public byte[] value;
  public int ttlSeconds; // 0 means no ttl

  public Set(byte[] hashKey, byte[] sortKey, byte[] value) {
    this(hashKey, sortKey, value, 0);
  }

  public Set(byte[] hashKey, byte[] sortKey, byte[] value, int ttlSeconds) {
    assert (value != null && ttlSeconds >= 0);
    this.hashKey = hashKey;
    this.sortKey = sortKey;
    this.value = value;
    this.ttlSeconds = ttlSeconds;
  }
}

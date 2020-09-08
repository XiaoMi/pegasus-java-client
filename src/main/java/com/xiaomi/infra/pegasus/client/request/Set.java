// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import java.io.Serializable;

public class Set implements Serializable {
  public final byte[] hashKey;
  public final byte[] sortKey;
  public final byte[] value;

  public int ttlSeconds;

  public Set(byte[] hashKey, byte[] sortKey, byte[] value) {
    assert value != null : "value != null";
    this.hashKey = hashKey;
    this.sortKey = sortKey;
    this.value = value;
    this.ttlSeconds = 0;
  }

  public Set withTTLSeconds(int ttlSeconds) {
    assert ttlSeconds > 0 : "ttlSeconds > 0";
    this.ttlSeconds = ttlSeconds;
    return this;
  }
}

// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import java.util.*;
import org.apache.commons.lang3.tuple.Pair;

public class MultiSet {
  public byte[] hashKey;
  public List<Pair<byte[], byte[]>> values = new ArrayList<>();
  public int ttlSeconds;

  public MultiSet(byte[] hashKey) {
    this(hashKey, 0);
  }

  public MultiSet(byte[] hashKey, int ttlSeconds) {
    assert (hashKey != null && hashKey.length > 0 && hashKey.length < 0xFFFF && ttlSeconds > 0);
    this.hashKey = hashKey;
    this.ttlSeconds = ttlSeconds;
  }

  public void add(byte[] sortKey, byte[] value) {
    values.add(Pair.of(sortKey, value));
  }
}

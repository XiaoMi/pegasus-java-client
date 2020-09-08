// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class MultiSet implements Serializable {

  private static final long serialVersionUID = -2236077975122224688L;

  public final byte[] hashKey;
  public final List<Pair<byte[], byte[]>> values;

  public int ttlSeconds;

  public MultiSet(byte[] hashKey) {
    this(hashKey, new ArrayList<>());
  }

  public MultiSet(byte[] hashKey, List<Pair<byte[], byte[]>> values) {
    checkArguments(hashKey, values);
    this.hashKey = hashKey;
    this.values = values;
    this.ttlSeconds = 0;
  }

  public MultiSet add(byte[] sortKey, byte[] value) {
    values.add(Pair.of(sortKey, value));
    return this;
  }

  public MultiSet withTTLSeconds(int ttlSeconds) {
    assert ttlSeconds > 0;
    this.ttlSeconds = ttlSeconds;
    return this;
  }

  private void checkArguments(byte[] hashKey, List<Pair<byte[], byte[]>> values) {
    assert (hashKey != null && hashKey.length > 0 && hashKey.length < 0xFFFF)
        : "hashKey != null && hashKey.length > 0 && hashKey.length < 0xFFFF";
    assert values != null : "values != null";
  }
}

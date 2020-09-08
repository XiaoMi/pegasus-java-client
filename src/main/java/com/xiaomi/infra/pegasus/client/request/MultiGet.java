// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MultiGet implements Serializable {

  private static final long serialVersionUID = 2068964280456524026L;

  public final byte[] hashKey;
  public final List<byte[]> sortKeys;

  public MultiGet(byte[] hashKey) {
    this(hashKey, new ArrayList<>());
  }

  public MultiGet(byte[] hashKey, List<byte[]> sortKeys) {
    checkArguments(hashKey, sortKeys);
    this.hashKey = hashKey;
    this.sortKeys = sortKeys;
  }

  public MultiGet add(byte[] sortKey) {
    sortKeys.add(sortKey);
    return this;
  }

  private void checkArguments(byte[] hashKey, List<byte[]> sortKeys) {
    assert (hashKey != null && hashKey.length > 0 && hashKey.length < 0xFFFF)
        : "hashKey != null && hashKey.length > 0 && hashKey.length < 0xFFFF";
    assert sortKeys != null : "sortKeys != null";
  }
}

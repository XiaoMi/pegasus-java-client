// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import java.io.Serializable;

public class Get implements Serializable {
  public final byte[] hashKey;
  public final byte[] sortKey;

  public Get(byte[] hashKey) {
    this(hashKey, null);
  }

  public Get(byte[] hashKey, byte[] sortKey) {
    this.hashKey = hashKey;
    this.sortKey = sortKey;
  }
}

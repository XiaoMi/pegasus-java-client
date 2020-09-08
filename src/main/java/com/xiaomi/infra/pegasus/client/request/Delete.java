// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import java.io.Serializable;

public class Delete implements Serializable {
  public final byte[] hashKey;
  public final byte[] sortKey;

  public Delete(byte[] hashKey) {
    this(hashKey, null);
  }

  public Delete(byte[] hashKey, byte[] sortKey) {
    this.hashKey = hashKey;
    this.sortKey = sortKey;
  }
}

// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;

public class BatchSet extends Batch<Set, Void> {

  private static final long serialVersionUID = 7339081203717442862L;

  public BatchSet(PegasusTableInterface table, int timeout) {
    super(table, timeout);
  }

  @Override
  protected Future<Void> asyncCommit(Set set) {
    return table.asyncSet(set.hashKey, set.sortKey, set.value, set.ttlSeconds, timeout);
  }
}

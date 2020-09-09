// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;

public class MultiSetBatch extends Batch<MultiSet, Void> {

  private static final long serialVersionUID = -7112478467009023481L;

  public MultiSetBatch(PegasusTableInterface table, int timeout) {
    super(table, timeout);
  }

  @Override
  protected Future<Void> asyncCommit(MultiSet multiSet) {
    return table.asyncMultiSet(multiSet.hashKey, multiSet.values, multiSet.ttlSeconds, timeout);
  }
}

// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;

public class BatchMultiDelete extends Batch<MultiDelete, Void> {

  private static final long serialVersionUID = 4692293597610231870L;

  public BatchMultiDelete(PegasusTableInterface table, int timeout) {
    super(table, timeout);
  }

  @Override
  protected Future<Void> asyncCommit(MultiDelete multiDelete) {
    return table.asyncMultiDel(multiDelete.hashKey, multiDelete.sortKeys, timeout);
  }
}

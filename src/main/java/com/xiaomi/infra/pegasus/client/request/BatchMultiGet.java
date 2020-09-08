// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface.MultiGetResult;
import io.netty.util.concurrent.Future;

public class BatchMultiGet extends Batch<MultiGet, MultiGetResult> {

  private static final long serialVersionUID = -8398629822956682881L;

  public BatchMultiGet(PegasusTableInterface table, int timeout) {
    super(table, timeout);
  }

  @Override
  protected Future<MultiGetResult> asyncCommit(MultiGet multiGet) {
    return table.asyncMultiGet(multiGet.hashKey, multiGet.sortKeys, timeout);
  }
}

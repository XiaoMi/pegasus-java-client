// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.util.List;

public class BatchGet extends Batch<Get, byte[]> {

  private static final long serialVersionUID = -8713375648386293450L;

  public BatchGet(List<Get> gets) {
    super(gets);
  }

  public BatchGet(PegasusTableInterface table, List<Get> gets, int timeout) {
    super(table, gets, timeout);
  }

  @Override
  public Future<byte[]> asyncCommit(Get get) {
    return table.asyncGet(get.hashKey, get.sortKey, timeout);
  }
}

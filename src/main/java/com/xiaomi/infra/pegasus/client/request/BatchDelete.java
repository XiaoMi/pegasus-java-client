// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.util.List;

public class BatchDelete extends Batch<Delete, Void> {

  private static final long serialVersionUID = -3749505015438921947L;

  public BatchDelete(List<Delete> deletes) {
    super(deletes);
  }

  public BatchDelete(PegasusTableInterface table, List<Delete> deletes, int timeout) {
    super(table, deletes, timeout);
  }

  @Override
  public Future<Void> asyncCommit(Delete delete) {
    return table.asyncDel(delete.hashKey, delete.sortKey, timeout);
  }
}

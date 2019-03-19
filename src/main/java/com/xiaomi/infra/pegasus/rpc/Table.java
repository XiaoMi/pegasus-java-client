// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.rpc;

import com.xiaomi.infra.pegasus.base.gpid;
import com.xiaomi.infra.pegasus.operator.*;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;

public abstract class Table {
  public interface ClientOPCallback {
    public void onCompletion(client_operator clientOP) throws Throwable;
  }

  public final long getHash(byte[] data) {
    return hasher_.hash(data);
  }

  public final long getKeyHash(byte[] data) {
    return KeyHasher.DEFAULT.hash(data);
  }

  public final gpid getGpidByHash(long hash_value) {
    com.xiaomi.infra.pegasus.base.gpid result = new com.xiaomi.infra.pegasus.base.gpid(appID_, -1);
    result.set_pidx((int) remainder_unsigned(hash_value, getPartitionCount()));
    return result;
  }

  public final gpid[] getAllGpid() {
    int count = getPartitionCount();
    com.xiaomi.infra.pegasus.base.gpid[] ret = new com.xiaomi.infra.pegasus.base.gpid[count];
    for (int i = 0; i < count; i++) {
      ret[i] = new com.xiaomi.infra.pegasus.base.gpid(appID_, i);
    }
    return ret;
  }

  public final String getTableName() {
    return tableName_;
  }

  public final int getAppID() {
    return appID_;
  }

  public final <T> DefaultPromise<T> newPromise() {
    return new DefaultPromise<T>(getExecutor());
  }

  public abstract int getDefaultTimeout();

  public abstract int getPartitionCount();

  public abstract void operate(client_operator op, int timeoutMs) throws ReplicationException;

  public abstract void asyncOperate(client_operator op, ClientOPCallback callback, int timeoutMs);

  public abstract EventExecutor getExecutor();

  protected String tableName_;
  protected int appID_;
  protected KeyHasher hasher_;

  public static long remainder_unsigned(long dividend, long divisor) {
    if (dividend > 0) {
      return dividend % divisor;
    }
    long reminder = (dividend >>> 1) % divisor * 2 + (dividend & 1);
    return reminder >= 0 && reminder < divisor ? reminder : reminder - divisor;
  }
}

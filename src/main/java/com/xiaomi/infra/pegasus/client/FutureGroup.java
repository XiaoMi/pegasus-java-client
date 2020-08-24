// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

import io.netty.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class FutureGroup<Result> {
  private boolean forceComplete;

  public FutureGroup(int initialCapacity) {
    this(initialCapacity, true);
  }

  public FutureGroup(int initialCapacity, boolean forceComplete) {
    this.asyncTasks = new ArrayList<>(initialCapacity);
    this.forceComplete = forceComplete;
  }

  public void add(Future<Result> task) {
    asyncTasks.add(task);
  }

  public void waitAllComplete(int timeoutMillis) throws PException {
    List<Pair<PException, Result>> results = new ArrayList<>();
    waitAllComplete(results, timeoutMillis);
  }

  /**
   * Waits until all future tasks complete but terminate if one fails.
   *
   * @param results .
   */
  public int waitAllComplete(List<Pair<PException, Result>> results, int timeoutMillis)
      throws PException {
    int timeLimit = timeoutMillis;
    long duration = 0;
    int count = 0;
    for (int i = 0; i < asyncTasks.size(); i++) {
      Future<Result> fu = asyncTasks.get(i);
      try {
        long startTs = System.currentTimeMillis();
        fu.await(timeLimit);
        duration = System.currentTimeMillis() - startTs;
        timeLimit -= duration;
      } catch (Exception e) {
        throw new PException("async task #[" + i + "] await failed: " + e.toString());
      }

      if (timeLimit < 0) {
        throw new PException(
            String.format("async task #[" + i + "] failed: timeout expired (%dms)", timeoutMillis));
      }

      if (fu.isSuccess()) {
        count++;
        results.add(Pair.of(null, fu.getNow()));
      } else {
        Throwable cause = fu.cause();
        if (forceComplete) {
          throw new PException("async task #[" + i + "] failed: " + cause.getMessage(), cause);
        }
        results.add(
            Pair.of(
                new PException("async task #[" + i + "] failed: " + cause.getMessage(), cause),
                null));
      }
    }
    return count;
  }

  private List<Future<Result>> asyncTasks;
}

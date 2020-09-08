// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.example;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusClientFactory;
import com.xiaomi.infra.pegasus.client.PegasusClientInterface;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import com.xiaomi.infra.pegasus.client.request.Batch;
import com.xiaomi.infra.pegasus.client.request.BatchDelete;
import com.xiaomi.infra.pegasus.client.request.BatchGet;
import com.xiaomi.infra.pegasus.client.request.BatchSet;
import com.xiaomi.infra.pegasus.client.request.Delete;
import com.xiaomi.infra.pegasus.client.request.Get;
import com.xiaomi.infra.pegasus.client.request.Set;
import io.netty.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class BatchSample {

  public void batch() throws PException {
    String tableName = "temp";
    PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
    PegasusTableInterface table = client.openTable(tableName);

    List<Set> sets = new ArrayList<>();
    sets.add(new Set("hashKeySet_1".getBytes(), "sortKeySet1".getBytes(), "valueSet1".getBytes()));
    sets.add(
        new Set("hashKeySet_2".getBytes(), "sortKeySet2".getBytes(), "valueSet2".getBytes())
            .withTTLSeconds(1000));

    List<Get> gets = new ArrayList<>();
    gets.add(new Get("hashKeySet_1".getBytes(), "sortKeySet1".getBytes()));
    gets.add(new Get("hashKeySet_2".getBytes(), "sortKeySet2".getBytes()));

    List<Delete> deletes = new ArrayList<>();
    deletes.add(new Delete("hashKeySet_1".getBytes(), "sortKeySet1".getBytes()));
    deletes.add(new Delete("hashKeySet_2".getBytes(), "sortKeySet2".getBytes()));

    List<byte[]> getResults = new ArrayList<>();
    // use client
    client.batch(tableName, new BatchSet(sets));
    client.batch(tableName, new BatchDelete(deletes));
    client.batch(tableName, new BatchGet(gets), getResults);

    List<Pair<PException, byte[]>> getResultsWithExp = new ArrayList<>();
    client.batchWaitAllComplete(tableName, new BatchGet(gets), getResultsWithExp);

    // use table
    table.batch(new BatchSet(sets), 1000);
    table.batch(new BatchDelete(deletes), 1000);
    table.batch(new BatchGet(gets), getResults, 1000);

    table.batchWaitAllComplete(new BatchGet(gets), getResultsWithExp, 1000);
  }

  public void batchCustom() throws PException {
    String tableName = "temp";
    PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
    PegasusTableInterface table = client.openTable(tableName);

    class Increment {
      public final byte[] hashKey;
      public final byte[] sortKey;
      final long value;

      private Increment(byte[] hashKey, byte[] sortKey, long value) {
        this.hashKey = hashKey;
        this.sortKey = sortKey;
        this.value = value;
      }
    }

    List<Increment> increments = new ArrayList<>();
    increments.add(new Increment("hashKeyIncr1".getBytes(), "sortKeyIncr1".getBytes(), 1));
    increments.add(new Increment("hashKeyIncr1".getBytes(), "sortKeyIncr1".getBytes(), 2));

    List<Long> incrResults = new ArrayList<>();
    // use client
    client.batch(
        tableName,
        new Batch<Increment, Long>(increments) {
          @Override
          public Future<Long> asyncCommit(Increment increment) {
            return table.asyncIncr(increment.hashKey, increment.sortKey, increment.value, timeout);
          }
        },
        incrResults);

    // use table
    table.batch(
        new Batch<Increment, Long>(increments) {
          @Override
          public Future<Long> asyncCommit(Increment increment) {
            return table.asyncIncr(increment.hashKey, increment.sortKey, increment.value, timeout);
          }
        },
        incrResults,
        1000);
  }
}

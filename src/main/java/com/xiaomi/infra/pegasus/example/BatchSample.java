// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.example;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusClientFactory;
import com.xiaomi.infra.pegasus.client.PegasusClientInterface;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import com.xiaomi.infra.pegasus.client.request.Batch;
import com.xiaomi.infra.pegasus.client.request.Delete;
import com.xiaomi.infra.pegasus.client.request.DeleteBatch;
import com.xiaomi.infra.pegasus.client.request.Get;
import com.xiaomi.infra.pegasus.client.request.GetBatch;
import com.xiaomi.infra.pegasus.client.request.Set;
import com.xiaomi.infra.pegasus.client.request.SetBatch;
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
    new SetBatch(table, 1000).commit(sets);
    new DeleteBatch(table, 1000).commit(deletes);

    GetBatch getBatch = new GetBatch(table, 1000);
    getBatch.commit(gets, getResults);

    List<Pair<PException, byte[]>> getResultsWithExp = new ArrayList<>();
    getBatch.commitWaitAllComplete(gets, getResultsWithExp);

    PegasusClientFactory.closeSingletonClient();
  }

  public void batchCustom() throws PException {
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

    String tableName = "temp";
    PegasusTableInterface table = PegasusClientFactory.getSingletonClient().openTable(tableName);

    table.del("hashKeyIncr1".getBytes(), "sortKeyIncr1".getBytes(), 1000);

    List<Increment> increments = new ArrayList<>();
    increments.add(new Increment("hashKeyIncr1".getBytes(), "sortKeyIncr1".getBytes(), 1));
    increments.add(new Increment("hashKeyIncr1".getBytes(), "sortKeyIncr1".getBytes(), 2));

    List<Long> incrResults = new ArrayList<>();

    Batch<Increment, Long> batchIncr =
        new Batch<Increment, Long>(table, 1000) {
          @Override
          protected Future<Long> asyncCommit(Increment increment) {
            return table.asyncIncr(increment.hashKey, increment.sortKey, increment.value, timeout);
          }
        };

    batchIncr.commit(increments, incrResults);

    PegasusClientFactory.closeSingletonClient();
  }
}

// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface.MultiGetResult;
import com.xiaomi.infra.pegasus.client.request.Batch;
import com.xiaomi.infra.pegasus.client.request.BatchDelete;
import com.xiaomi.infra.pegasus.client.request.BatchGet;
import com.xiaomi.infra.pegasus.client.request.BatchMultiDelete;
import com.xiaomi.infra.pegasus.client.request.BatchMultiGet;
import com.xiaomi.infra.pegasus.client.request.BatchMultiSet;
import com.xiaomi.infra.pegasus.client.request.BatchSet;
import com.xiaomi.infra.pegasus.client.request.Delete;
import com.xiaomi.infra.pegasus.client.request.Get;
import com.xiaomi.infra.pegasus.client.request.MultiDelete;
import com.xiaomi.infra.pegasus.client.request.MultiGet;
import com.xiaomi.infra.pegasus.client.request.MultiSet;
import com.xiaomi.infra.pegasus.client.request.Set;
import io.netty.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TestBatch {

  @Test
  public void testBatchSetDelGet() throws PException, InterruptedException {
    PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
    String tableName = "temp";

    List<Set> sets = new ArrayList<>();
    sets.add(new Set("hashKeySet_1".getBytes(), "sortKeySet1".getBytes(), "valueSet1".getBytes()));
    sets.add(new Set("hashKeySet_2".getBytes(), "sortKeySet2".getBytes(), "valueSet2".getBytes()));
    sets.add(new Set("hashKeySet_3".getBytes(), "sortKeySet3".getBytes(), "valueSet3".getBytes()));
    sets.add(
        new Set("hashKeySet_4".getBytes(), "sortKeySet4".getBytes(), "valueSet4WithTTL".getBytes())
            .withTTLSeconds(10));

    List<Get> gets = new ArrayList<>();
    gets.add(new Get("hashKeySet_1".getBytes(), "sortKeySet1".getBytes()));
    gets.add(new Get("hashKeySet_2".getBytes(), "sortKeySet2".getBytes()));
    gets.add(new Get("hashKeySet_3".getBytes(), "sortKeySet3".getBytes()));
    gets.add(new Get("hashKeySet_4".getBytes(), "sortKeySet4".getBytes()));

    List<Delete> deletes = new ArrayList<>();
    deletes.add(new Delete("hashKeySet_1".getBytes(), "sortKeySet1".getBytes()));
    deletes.add(new Delete("hashKeySet_2".getBytes(), "sortKeySet2".getBytes()));

    List<byte[]> getResults = new ArrayList<>();
    client.batch(tableName, new BatchSet(sets));
    client.batch(tableName, new BatchDelete(deletes));
    client.batch(tableName, new BatchGet(gets), getResults);
    Assertions.assertNull(getResults.get(0));
    Assertions.assertNull(getResults.get(1));
    Assertions.assertEquals("valueSet3", new String(getResults.get(2)));
    Assertions.assertEquals("valueSet4WithTTL", new String(getResults.get(3)));

    Thread.sleep(11000);

    List<Pair<PException, byte[]>> getResultsWithExp = new ArrayList<>();
    client.batchWaitAllComplete(tableName, new BatchGet(gets), getResultsWithExp);
    Assertions.assertNull(getResultsWithExp.get(2).getKey());
    Assertions.assertEquals("valueSet3", new String(getResultsWithExp.get(2).getRight()));
    Assertions.assertNull(getResultsWithExp.get(3).getRight());

    PegasusClientFactory.closeSingletonClient();
  }

  @Test
  public void testBatchMultiSetDelGet() throws PException {
    PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
    String tableName = "temp";

    List<MultiSet> multiSets = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      MultiSet multiSet = new MultiSet(("hashKeyMultiSet" + i).getBytes());
      for (int j = 0; j < 3; j++) {
        multiSet.add(("sortKeyMultiSet" + j).getBytes(), ("valueMultiSet" + j).getBytes());
      }
      multiSets.add(multiSet);
    }

    List<MultiDelete> multiDeletes = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      MultiDelete multiDelete = new MultiDelete(("hashKeyMultiSet" + i).getBytes());
      for (int j = 0; j < 3; j++) {
        multiDelete.add(("sortKeyMultiSet" + j).getBytes());
      }
      multiDeletes.add(multiDelete);
    }

    List<MultiGet> multiGets = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      MultiGet multiGet = new MultiGet(("hashKeyMultiSet" + i).getBytes());
      for (int j = 0; j < 3; j++) {
        multiGet.add(("sortKeyMultiSet" + j).getBytes());
      }
      multiGets.add(multiGet);
    }

    List<MultiGetResult> multiGetResults = new ArrayList<>();
    client.batch(tableName, new BatchMultiSet(multiSets));
    client.batch(tableName, new BatchMultiDelete(multiDeletes));
    client.batch(tableName, new BatchMultiGet(multiGets), multiGetResults);

    Assertions.assertEquals(0, multiGetResults.get(0).values.size());
    Assertions.assertEquals(0, multiGetResults.get(1).values.size());
    Assertions.assertTrue(multiGetResults.get(2).allFetched);
    for (int i = 0; i < 3; i++) {
      Assertions.assertEquals(
          "sortKeyMultiSet" + i, new String(multiGetResults.get(2).values.get(i).getKey()));
      Assertions.assertEquals(
          "valueMultiSet" + i, new String(multiGetResults.get(2).values.get(i).getRight()));
    }

    List<Pair<PException, MultiGetResult>> multiGetResultsWithExp = new ArrayList<>();
    client.batchWaitAllComplete(tableName, new BatchMultiGet(multiGets), multiGetResultsWithExp);
    for (int i = 0; i < 3; i++) {
      Assertions.assertNull(multiGetResultsWithExp.get(2).getLeft());
      Assertions.assertEquals(
          "sortKeyMultiSet" + i,
          new String(multiGetResultsWithExp.get(2).getRight().values.get(i).getKey()));
      Assertions.assertEquals(
          "valueMultiSet" + i,
          new String(multiGetResultsWithExp.get(2).getRight().values.get(i).getRight()));
    }

    PegasusClientFactory.closeSingletonClient();
  }

  @Test
  public void testBatchCustomRequest() throws PException {
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

    PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
    String tableName = "temp";

    client.del(tableName, "hashKeyIncr1".getBytes(), "sortKeyIncr1".getBytes());

    List<Increment> increments = new ArrayList<>();
    increments.add(new Increment("hashKeyIncr1".getBytes(), "sortKeyIncr1".getBytes(), 1));
    increments.add(new Increment("hashKeyIncr1".getBytes(), "sortKeyIncr1".getBytes(), 2));

    List<Long> incrResults = new ArrayList<>();
    client.batch(
        tableName,
        new Batch<Increment, Long>(increments) {
          @Override
          public Future<Long> asyncCommit(Increment increment) {
            return table.asyncIncr(increment.hashKey, increment.sortKey, increment.value, timeout);
          }
        },
        incrResults);

    Assertions.assertEquals(1, incrResults.get(0).longValue());
    Assertions.assertEquals(3, incrResults.get(1).longValue());

    PegasusClientFactory.closeSingletonClient();
  }
}

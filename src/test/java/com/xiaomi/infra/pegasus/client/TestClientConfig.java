// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

import com.xiaomi.infra.pegasus.rpc.Table;
import com.xiaomi.infra.pegasus.rpc.async.ClusterManager;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class TestClientConfig {
  @Test
  public void testMaxFetchConfig() throws Exception {
    int testFetchCount = 100;
    int testFetchBytes = 1 * 1024 * 1024;

    PegasusClient client = (PegasusClient) (PegasusClientFactory.getSingletonClient());
    ClientOptions options = (ClientOptions) FieldUtils.readField(client, "clientOptions", true);
    Assert.assertEquals(options.getMaxFetchCount(), testFetchCount);
    Assert.assertEquals(options.getMaxFetchBytes(), testFetchBytes);
    ClusterManager clusterManager = (ClusterManager) FieldUtils.readField(client, "cluster", true);
    Assert.assertEquals(clusterManager.getMaxFetchCount(), testFetchCount);
    Assert.assertEquals(clusterManager.getMaxFetchBytes(), testFetchBytes);
    PegasusTable table = (PegasusTable) client.openTable("temp");
    Table innerTable = (Table) FieldUtils.readField(table, "table", true);
    Assert.assertEquals(innerTable.getDefaultMaxFetchCount(), testFetchCount);
    Assert.assertEquals(innerTable.getDefaultMaxFetchBytes(), testFetchBytes);
    client.close();

    testFetchCount = 19;
    testFetchBytes = 19999999;
    client =
        (PegasusClient)
            (PegasusClientFactory.createClient(
                ClientOptions.builder()
                    .maxFetchCount(testFetchCount)
                    .maxFetchBytes(testFetchBytes)
                    .build()));
    options = (ClientOptions) FieldUtils.readField(client, "clientOptions", true);
    Assert.assertEquals(options.getMaxFetchCount(), testFetchCount);
    Assert.assertEquals(options.getMaxFetchBytes(), testFetchBytes);
    clusterManager = (ClusterManager) FieldUtils.readField(client, "cluster", true);
    Assert.assertEquals(clusterManager.getMaxFetchCount(), testFetchCount);
    Assert.assertEquals(clusterManager.getMaxFetchBytes(), testFetchBytes);
    table = (PegasusTable) client.openTable("temp");
    innerTable = (Table) FieldUtils.readField(table, "table", true);
    Assert.assertEquals(innerTable.getDefaultMaxFetchCount(), testFetchCount);
    Assert.assertEquals(innerTable.getDefaultMaxFetchBytes(), testFetchBytes);
    client.close();

    testFetchCount = 10;
    testFetchBytes = 1 * 1024 * 1024;
    client =
        (PegasusClient)
            (PegasusClientFactory.createClient(
                ClientOptions.builder()
                    .maxFetchCount(testFetchCount)
                    .maxFetchBytes(testFetchBytes)
                    .build()));
    String testHashKey = "testHashKey_" + System.currentTimeMillis();
    List<byte[]> sortKeyList = new ArrayList<>();
    for (int i = 0; i < 11; ++i) {
      String testSortKey = "testSortKey_" + i + System.currentTimeMillis();
      String testValue = "testValue_" + i + System.currentTimeMillis();
      client.set("temp", testHashKey.getBytes(), testSortKey.getBytes(), testValue.getBytes());
      sortKeyList.add(testSortKey.getBytes());
    }
    List<Pair<byte[], List<byte[]>>> requestList = new ArrayList<>();
    requestList.add(Pair.of(testHashKey.getBytes(), sortKeyList));
    List<HashKeyData> values = new ArrayList<>();
    client.batchMultiGet("temp", requestList, values);
    Assert.assertEquals(1, values.size());
    Assert.assertFalse(values.get(0).allFetched);
    Assert.assertEquals(values.get(0).values.size(), 10);

    requestList.clear();
    values.clear();
    sortKeyList.remove(0);
    sortKeyList.remove(0);
    requestList.add(Pair.of(testHashKey.getBytes(), sortKeyList));
    client.batchMultiGet("temp", requestList, values);
    Assert.assertEquals(1, values.size());
    Assert.assertTrue(values.get(0).allFetched);
    Assert.assertEquals(values.get(0).values.size(), 9);
    client.close();
  }
}

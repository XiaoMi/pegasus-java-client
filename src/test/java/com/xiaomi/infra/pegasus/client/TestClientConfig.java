// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

import com.xiaomi.infra.pegasus.rpc.Table;
import com.xiaomi.infra.pegasus.rpc.async.ClusterManager;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Test;

public class TestClientConfig {
  @Test
  public void testMaxFetchConfig() throws Exception {
    PegasusClient client = (PegasusClient) (PegasusClientFactory.getSingletonClient());
    ClientOptions options = (ClientOptions) FieldUtils.readField(client, "clientOptions", true);
    Assert.assertEquals(options.getMaxFetchCount(), 100);
    Assert.assertEquals(options.getMaxFetchBytes(), 1 * 1024 * 1024);
    ClusterManager clusterManager = (ClusterManager) FieldUtils.readField(client, "cluster", true);
    Assert.assertEquals(clusterManager.getMaxFetchCount(), 100);
    Assert.assertEquals(clusterManager.getMaxFetchBytes(), 1 * 1024 * 1024);
    PegasusTable table = (PegasusTable) client.openTable("temp");
    Table innerTable = (Table) FieldUtils.readField(table, "table", true);
    Assert.assertEquals(innerTable.getDefaultMaxFetchCount(), 100);
    Assert.assertEquals(innerTable.getDefaultMaxFetchBytes(), 1 * 1024 * 1024);
  }
}

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.xiaomi.infra.pegasus.client;

import com.xiaomi.infra.pegasus.base.rpc_address;
import com.xiaomi.infra.pegasus.tools.Toollet;
import java.util.HashMap;
import org.junit.*;

public class TestAdminClient {
  PegasusAdminClientInterface toolsClient;
  final String metaServerList = "127.0.0.1:34601,127.0.0.1:34602,127.0.0.1:34603";

  @Before
  public void Setup() throws PException {
    ClientOptions clientOptions =
        ClientOptions.builder()
            .metaServers(this.metaServerList)
            .asyncWorkers(6)
            .enablePerfCounter(false)
            .build();

    toolsClient = PegasusAdminClientFactory.createClient(clientOptions);
  }

  @After
  public void after() {
    toolsClient.close();
  }

  private void testOneCreateApp(String appName) throws PException {
    int partitionCount = 8;
    int replicaCount = 3;
    int opTimeoutMs = 66000;
    toolsClient.createApp(appName, partitionCount, replicaCount, new HashMap<>(), opTimeoutMs);

    boolean isAppHealthy = toolsClient.isAppHealthy(appName, replicaCount);

    Assert.assertTrue(isAppHealthy);

    replicaCount = 5;
    isAppHealthy = toolsClient.isAppHealthy(appName, replicaCount);
    Assert.assertFalse(isAppHealthy);
  }

  @Test
  public void testCreateNewApp() throws PException {
    String appName = "testCreateApp1";
    testOneCreateApp(appName);
  }

  @Test
  public void testCreateNewAppConsideringMetaForward() throws PException {
    String[] metaServerArray = this.metaServerList.split(",");
    for (int i = 0; i < metaServerArray.length; ++i) {
      rpc_address address = new rpc_address();
      address.fromString(metaServerArray[i]);

      Toollet.closeServer(address);

      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      if (i > 0) {
        rpc_address preMetaAddress = new rpc_address();
        preMetaAddress.fromString(metaServerArray[i - 1]);
        Toollet.tryStartServer(preMetaAddress);
      }

      String appName = "testMetaForward_" + i;
      testOneCreateApp(appName);
    }

    rpc_address address = new rpc_address();
    address.fromString(metaServerArray[metaServerArray.length - 1]);
    Toollet.tryStartServer(address);
  }

  @Test
  public void testIsAppHealthyIfTableNotExists() throws PException {
    // test a not existed app
    String appName = "testIsAppHealthyIfNotExists";
    int replicaCount = 3;

    try {
      toolsClient.isAppHealthy(appName, replicaCount);
    } catch (PException e) {
      return;
    }

    Assert.fail();
  }
}

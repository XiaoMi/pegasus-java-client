package com.xiaomi.infra.pegasus.client;

import org.junit.*;

public class TestAdminClient {
  PegasusAdminClientInterface toolsClient;

  @Before
  public void Setup() throws PException {
    ClientOptions clientOptions =
        ClientOptions.builder()
            .metaServers("127.0.0.1:34601,127.0.0.1:34602,127.0.0.1:34603")
            .asyncWorkers(6)
            .enablePerfCounter(false)
            .build();

    toolsClient = PegasusAdminClientFactory.createClient(clientOptions);
  }

  @After
  public void after() {
    toolsClient.close();
  }

  @Test
  public void testCreateNewApp() throws PException {
    String appName = "testCreateApp1";
    int partitionCount = 8;
    int replicaCount = 3;
    int opTimeoutMs = 66000;
    toolsClient.createApp(appName, partitionCount, replicaCount, opTimeoutMs);

    boolean isAppHealthy = toolsClient.isAppHealthy(appName, replicaCount, opTimeoutMs);

    Assert.assertTrue(isAppHealthy);

    replicaCount = 5;
    isAppHealthy = toolsClient.isAppHealthy(appName, replicaCount, opTimeoutMs);
    Assert.assertFalse(isAppHealthy);
  }

  @Test
  public void testIsAppHealthyIfTableNotExists() throws PException {
    // test a not existed app
    String appName = "testIsAppHealthyIfNotExists";
    int replicaCount = 3;
    int opTimeoutMs = 6000;

    try {
      toolsClient.isAppHealthy(appName, replicaCount, opTimeoutMs);
    } catch (PException e) {
      return;
    }

    Assert.fail();
  }
}

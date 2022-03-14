package com.xiaomi.infra.pegasus.client;

import org.junit.*;

public class TestToolsClient {
  PegasusToolsClientInterface toolsClient;

  @Before
  public void Setup() throws PException {
    ClientOptions clientOptions =
        ClientOptions.builder()
            .metaServers("127.0.0.1:34601,127.0.0.1:34602,127.0.0.1:34603")
            .asyncWorkers(6)
            .enablePerfCounter(false)
            .build();

    toolsClient = PegasusToolsClientFactory.createClient(clientOptions);
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
    int opTimeoutMs = 10000;
    int checkRemainCount = 66;

    toolsClient.createApp(appName, partitionCount, replicaCount, opTimeoutMs);

    boolean isAppHealthy = toolsClient.isAppHealthy(appName, replicaCount, opTimeoutMs);
    int totalTryCount = 1;
    while (!isAppHealthy) {
      if (totalTryCount >= checkRemainCount) {
        break;
      }

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        continue;
      }

      isAppHealthy = toolsClient.isAppHealthy(appName, replicaCount, opTimeoutMs);
      ++totalTryCount;
    }

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

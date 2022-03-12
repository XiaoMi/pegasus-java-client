package com.xiaomi.infra.pegasus.client;

import org.junit.Assert;
import org.junit.Test;

public class TestToolsClient {
  @Test
  public void testCreateNewApp() throws PException {
    ClientOptions clientOptions =
        ClientOptions.builder()
            .metaServers("127.0.0.1:34601,127.0.0.1:34602,127.0.0.1:34603")
            .asyncWorkers(6)
            .build();

    String appName = "testCreateApp1";
    int partitionCount = 8;
    int replicaCount = 3;

    PegasusToolsClientInterface toolsClient = PegasusToolsClientFactory.createClient(clientOptions);
    toolsClient.createApp(appName, partitionCount, replicaCount, 6000);

    boolean isAppReady = toolsClient.isAppReady(appName, partitionCount, replicaCount);
    int totalTryCount = 1;
    while(!isAppReady) {
      if (totalTryCount >= 8) {
        break;
      }

      try {
        Thread.sleep(1000);
      } catch(InterruptedException e) {
        continue;
      }

      isAppReady = toolsClient.isAppReady(appName, partitionCount, replicaCount);
      ++totalTryCount;
    }

    Assert.assertTrue(isAppReady);
  }

  @Test
  public void testCreateNewAppTimeout() throws PException {

  }

  @Test
  public void testIsAppReady() throws PException {

  }
}

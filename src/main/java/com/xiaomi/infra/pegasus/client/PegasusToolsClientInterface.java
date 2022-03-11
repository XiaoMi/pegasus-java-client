// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

public interface PegasusToolsClientInterface {
  /**
   * Create A new pegasus app which is not stateless
   *
   * @param appName App name which will be created by this interface
   * @param partitionCount The partition count of the newly creating app
   * @param replicaCount The replica count of the newly creating app
   * @param timeoutMs The timeout of this function call, milli-seconds
   * @throws PException
   */
  public void createApp(String appName, int partitionCount, int replicaCount, long timeoutMs)
      throws PException;

  /**
   * Judge If An App Is 'ready'(every partition of the app has enough replicas)
   *
   * @param appName App name which will be judged ready or not by this interface
   * @param partitionCount partitionCount of the app
   * @param replicaCount replicaCount of the app
   * @return true if the app has enough healthy replicas, otherwise return false
   * @throws PException
   */
  public boolean isAppReady(String appName, int partitionCount, int replicaCount) throws PException;
}

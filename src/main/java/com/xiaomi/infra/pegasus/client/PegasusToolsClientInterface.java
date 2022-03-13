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
   * @param timeoutMs The timeout of the interface, milli-seconds
   * @throws PException
   */
  public void createApp(String appName, int partitionCount, int replicaCount, long timeoutMs)
      throws PException;

  /**
   * Judge If An App Is 'healthy'(every partition of the app has enough replicas specified by the
   * 'replicaCount' parameter)
   *
   * @param appName App name which will be judged 'healthy' or not by this interface
   * @param replicaCount replicaCount of the app
   * @param timeoutMs The timeout of the interface, milli-seconds
   * @return true if the app in the pegasus server side has enough healthy replicas specified by the
   *     'replicaCount' parameter, otherwise return false
   * @throws PException If 'appName' not exists or the rpc to pegasus server cause timeout or other
   *     error happens in the server side, the interface will throw PException
   */
  public boolean isAppHealthy(String appName, int replicaCount, long timeoutMs) throws PException;

  /** close the client */
  public void close();
}

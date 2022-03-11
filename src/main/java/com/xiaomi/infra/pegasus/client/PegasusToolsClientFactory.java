// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

import java.util.Properties;

public class PegasusToolsClientFactory {
  /**
   * Create a client instance. After used, should call client.close() to release resource.
   *
   * @param configPath client config path,could be:
   *     <pre>
   * - zookeeper path  : zk://host1:port1,host2:port2,host3:port3/path/to/config
   * - local file path : file:///path/to/config
   * - java resource   : resource:///path/to/config</pre>
   *
   * @return PegasusToolsClientInterface {@link PegasusToolsClientInterface}
   * @throws PException throws exception if any error occurs.
   */
  public static PegasusToolsClientInterface createClient(String configPath) throws PException {
    return new PegasusToolsClient(configPath);
  }

  /**
   * Create a client instance. After used, should call client.close() to release resource.
   *
   * @param properties properties
   * @return PegasusToolsClientInterface {@link PegasusToolsClientInterface}
   * @throws PException throws exception if any error occurs.
   */
  public static PegasusToolsClientInterface createClient(Properties properties) throws PException {
    return new PegasusToolsClient(properties);
  }

  /**
   * Create a client instance instance with {@link ClientOptions}. After used, should call
   * client.close() to release resource.
   *
   * @param clientOptions The client option
   * @return PegasusToolsClientInterface {@link PegasusToolsClientInterface}
   * @throws PException throws exception if any error occurs.
   */
  public static PegasusToolsClientInterface createClient(ClientOptions clientOptions)
      throws PException {
    return new PegasusToolsClient(clientOptions);
  }
}

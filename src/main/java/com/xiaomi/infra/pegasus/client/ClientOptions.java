package com.xiaomi.infra.pegasus.client;
// Copyright (c) 2019, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.

public class ClientOptions {
  String metaServers = "127.0.0.1:34601,127.0.0.1:34602,127.0.0.1:34603";
  int timeout = 1000;

  public ClientOptions() {}

  public ClientOptions(String metaServers, int timeout) {
    this.metaServers = metaServers;
    this.timeout = timeout;
  }

  @Override
  public boolean equals(Object options) {
    if (this == options) {
      return true;
    }
    if (options instanceof ClientOptions) {
      ClientOptions clientOptions = (ClientOptions) options;
      if (this.metaServers.equals(clientOptions.metaServers)
          && this.timeout == clientOptions.timeout) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "ClientOptions{" + "metaServers='" + metaServers + '\'' + ", timeout=" + timeout + '}';
  }
}

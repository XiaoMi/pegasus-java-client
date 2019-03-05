// Copyright (c) 2019, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.rpc.async;

import com.xiaomi.infra.pegasus.base.rpc_address;

/*
 * Resolves host:port into a set of ip addresses.
 * The intention of this class is to mock DNS.
 */
abstract class HostNameResolver {
  rpc_address[] resolve(String hostPort) {
    return rpc_address.resolveFromHostPort(hostPort);
  }
}

// Copyright (c) 2019, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.

package com.xiaomi.infra.pegasus.base;

import org.junit.Assert;
import org.junit.Test;

public class TestRpcAddress {
  @Test
  public void testResolveFromHostPort() throws Exception {
    rpc_address[] addrs = rpc_address.resolveFromHostPort("127.0.0.1:34601");

    Assert.assertNotNull(addrs);
    Assert.assertEquals(addrs.length, 1);
    Assert.assertEquals(addrs[0].get_ip(), "127.0.0.1");
    Assert.assertEquals(addrs[0].get_port(), 34601);

    addrs = rpc_address.resolveFromHostPort("www.baidu.com:80");
    Assert.assertNotNull(addrs);
    Assert.assertTrue(addrs.length >= 1);

    addrs = rpc_address.resolveFromHostPort("abcabcabcabc:34601");
    Assert.assertNull(addrs);

    addrs = rpc_address.resolveFromHostPort("localhost");
    Assert.assertNull(addrs);
  }

  @Test
  public void testFromString() throws Exception {
    rpc_address addr = new rpc_address();
    Assert.assertTrue(addr.fromString("127.0.0.1:34601"));
    Assert.assertEquals(addr.get_ip(), "127.0.0.1");
    Assert.assertEquals(addr.get_port(), 34601);
  }
}

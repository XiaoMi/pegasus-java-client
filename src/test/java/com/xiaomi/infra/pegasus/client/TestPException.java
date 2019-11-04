// Copyright (c) 2019, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.

package com.xiaomi.infra.pegasus.client;

import java.util.concurrent.TimeoutException;
import org.junit.Assert;
import org.junit.Test;

public class TestPException {
  @Test
  public void testThreadInterrupted() throws Exception {
    PException ex = PException.threadInterrupted("test", new InterruptedException("intxxx"));
    Assert.assertEquals(
        "{version}: com.xiaomi.infra.pegasus.rpc.ReplicationException: ERR_THREAD_INTERRUPTED: [table=test] Thread was interrupted: intxxx",
        ex.getMessage());
  }

  @Test
  public void testTimeout() throws Exception {
    PException ex = PException.timeout("test", 1000, new TimeoutException("tmxxx"));
    Assert.assertEquals(
        "{version}: com.xiaomi.infra.pegasus.rpc.ReplicationException: ERR_TIMEOUT: [table=test, timeout=1000ms] Timeout on Future await: tmxxx",
        ex.getMessage());
  }

  @Test
  public void testVersion() {
    PException ex = new PException("test");
    Assert.assertEquals("{version}: test", ex.getMessage());

    ex = new PException("test", new TimeoutException());
    Assert.assertEquals("{version}: test", ex.getMessage());
  }
}

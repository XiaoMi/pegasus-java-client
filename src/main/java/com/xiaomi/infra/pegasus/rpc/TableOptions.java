// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.rpc;

import com.xiaomi.infra.pegasus.client.PegasusClient.PegasusHasher;

/** TableOptions is the internal options for opening a Pegasus table. */
public class TableOptions {
  private final KeyHasher keyHasher;
  private final int backupRequestDelayMs;
  private final boolean enableCompression;

  public TableOptions() {
    this.keyHasher = new PegasusHasher();
    this.backupRequestDelayMs = 0;
    this.enableCompression = false;
  }

  public TableOptions(int backupRequestDelay, boolean enableCompression) {
    this.keyHasher = new PegasusHasher();
    this.backupRequestDelayMs = backupRequestDelay;
    this.enableCompression = enableCompression;
  }

  public TableOptions(KeyHasher h, int backupRequestDelay, boolean enableCompression) {
    this.keyHasher = h;
    this.backupRequestDelayMs = backupRequestDelay;
    this.enableCompression = enableCompression;
  }

  public KeyHasher keyHasher() {
    return this.keyHasher;
  }

  public int backupRequestDelayMs() {
    return this.backupRequestDelayMs;
  }

  public static TableOptions forTest() {
    return new TableOptions(KeyHasher.DEFAULT, 0, false);
  }

  public boolean enableBackupRequest() {
    return backupRequestDelayMs > 0;
  }

  public boolean enableCompression() {
    return enableCompression;
  }
}

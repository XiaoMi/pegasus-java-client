// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.rpc;

import com.xiaomi.infra.pegasus.client.PegasusClient.PegasusHasher;

/** TableOptions is the internal options for opening a Pegasus table. */
public class TableOptions {
  private KeyHasher keyHasher;
  private int backupRequestDelayMs;
  public long retryTimeMs;
  private boolean enableCompression;

  public TableOptions() {
    this.keyHasher = new PegasusHasher();
    this.backupRequestDelayMs = 0;
    this.enableCompression = false;
  }

  public TableOptions withKeyHasher(KeyHasher keyHasher) {
    this.keyHasher = keyHasher;
    return this;
  }

  public TableOptions withBackupRequestDelayMs(int backupRequestDelayMs) {
    this.backupRequestDelayMs = backupRequestDelayMs;
    return this;
  }

  public TableOptions withCompression(boolean enableCompression) {
    this.enableCompression = enableCompression;
    return this;
  }

  public KeyHasher keyHasher() {
    return this.keyHasher;
  }

  public int backupRequestDelayMs() {
    return this.backupRequestDelayMs;
  }

  public boolean enableBackupRequest() {
    return backupRequestDelayMs > 0;
  }

  public boolean enableCompression() {
    return enableCompression;
  }

  public boolean enableAutoRetry() {
    return retryTimeMs > 0;
  }

  public static TableOptions forTest() {
    return new TableOptions().withKeyHasher(KeyHasher.DEFAULT);
  }
}

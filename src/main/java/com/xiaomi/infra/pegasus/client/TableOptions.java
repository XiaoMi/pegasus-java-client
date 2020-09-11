// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

/** TableOptions is the internal options for opening a Pegasus table. */
public class TableOptions {
  private boolean enableCompression;

  public TableOptions() {
    this.enableCompression = false;
  }

  public TableOptions withCompression(boolean enableCompression) {
    this.enableCompression = enableCompression;
    return this;
  }

  public boolean enableCompression() {
    return enableCompression;
  }
}

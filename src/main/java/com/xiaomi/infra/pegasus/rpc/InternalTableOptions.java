package com.xiaomi.infra.pegasus.rpc;

import com.xiaomi.infra.pegasus.client.PegasusClient.PegasusHasher;
import com.xiaomi.infra.pegasus.client.TableOptions;

public class InternalTableOptions {
  private final KeyHasher keyHasher;
  private final TableOptions tableOptions;

  public InternalTableOptions() {
    this.keyHasher = new PegasusHasher();
    this.tableOptions = new TableOptions();
  }

  public InternalTableOptions(KeyHasher keyHasher, TableOptions tableOptions) {
    this.keyHasher = keyHasher;
    this.tableOptions = tableOptions;
  }

  public KeyHasher keyHasher() {
    return keyHasher;
  }

  public TableOptions tableOptions() {
    return tableOptions;
  }
}

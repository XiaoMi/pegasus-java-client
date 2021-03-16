package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTable;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import com.xiaomi.infra.pegasus.client.ScanOptions;

public abstract class Range<Response> {
  public PegasusTableInterface table;
  protected byte[] hashKey;
  protected int timeout;

  protected ScanOptions scanOptions = new ScanOptions();
  protected byte[] startSortKey;
  protected byte[] stopSortKey;

  public Range(PegasusTableInterface table, byte[] hashKey, int timeout) {
    this.table = table;
    this.hashKey = hashKey;
    this.timeout = timeout <= 0 ? ((PegasusTable) table).getDefaultTimeout() : timeout;
  }

  public abstract Response commitAndWait(int maxRangeCount) throws PException;

  public Range<Response> withOptions(ScanOptions scanOptions) {
    this.scanOptions = scanOptions;
    return this;
  }

  public Range<Response> withStartSortKey(byte[] startSortKey) {
    this.startSortKey = startSortKey;
    return this;
  }

  public Range<Response> withStopSortKey(byte[] stopSortKey) {
    this.stopSortKey = stopSortKey;
    return this;
  }
}

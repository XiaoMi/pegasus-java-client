package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTable;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;

public class GetRange extends Range<PegasusTable.ScanRangeResult> {
  public PegasusTableInterface table;

  public GetRange(PegasusTableInterface table, byte[] hashKey, int timeout) {
    super(hashKey, timeout);
    this.table = table;
  }

  public PegasusTable.ScanRangeResult commitAndWait(int maxFetchCount) throws PException {
    ScannerWrapper<PegasusTable.ScanRangeResult> scannerWrapper = new ScannerWrapper<>(table, this);
    return scannerWrapper.hashScan(maxFetchCount, timeout);
  }
}

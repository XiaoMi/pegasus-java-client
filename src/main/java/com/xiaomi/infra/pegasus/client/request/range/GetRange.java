package com.xiaomi.infra.pegasus.client.request.range;

import static com.xiaomi.infra.pegasus.client.request.range.ScannerWrapper.Result;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;

public class GetRange extends Range<Result> {

  public GetRange(PegasusTableInterface table, byte[] hashKey, int timeout) {
    super(table, hashKey, timeout);
  }

  public Result commitAndWait(int maxFetchCount) throws PException {
    ScannerWrapper<Result> scannerWrapper = new ScannerWrapper<>(this);
    return scannerWrapper.hashScan(maxFetchCount);
  }
}

package com.xiaomi.infra.pegasus.client.request.range;

import static com.xiaomi.infra.pegasus.client.request.range.ScannerWrapper.Result;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;

public class GetRangeNoValue extends Range<Result> {

  public GetRangeNoValue(PegasusTableInterface table, byte[] hashKey, int timeout) {
    super(table, hashKey, timeout);
  }

  @Override
  public Result commitAndWait(int maxFetchCount) throws PException {
    this.scanOptions.noValue = true;
    ScannerWrapper<Result> scannerWrapper = new ScannerWrapper<>(this);
    return scannerWrapper.hashScan(maxFetchCount);
  }
}

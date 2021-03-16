package com.xiaomi.infra.pegasus.client.request.range;

import static com.xiaomi.infra.pegasus.client.request.range.ScannerWrapper.Result;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTable;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DeleteRange extends Range<Boolean> {

  public DeleteRange(PegasusTableInterface table, byte[] hashKey, int timeout) {
    super(table, hashKey, timeout);
  }

  public Boolean commitAndWait(int maxDeleteCount) throws PException {
    long deadlineTime = System.currentTimeMillis() + timeout;

    this.scanOptions.noValue = true;
    ScannerWrapper<Boolean> scannerWrapper = new ScannerWrapper<>(this);
    Result result = scannerWrapper.hashScan(maxDeleteCount);
    int remainingTime = (int) (deadlineTime - System.currentTimeMillis());
    if (remainingTime <= 0) {
      throw PException.timeout(
          ((PegasusTable) table).getMetaList(),
          ((PegasusTable) table).getTable().getTableName(),
          new PegasusTable.Request(hashKey),
          timeout,
          new TimeoutException());
    }

    try {
      table
          .asyncMultiDel(hashKey, result.convertMultiGetSortKeysResult().keys, remainingTime)
          .get(remainingTime, TimeUnit.MILLISECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new PException("Delete range failed!", e);
    }

    return result.allFetched;
  }
}

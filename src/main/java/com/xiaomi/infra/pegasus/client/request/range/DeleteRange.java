package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTable;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang3.tuple.Pair;

public class DeleteRange extends Range<Boolean> {
  public PegasusTableInterface table;
  private byte[] nextSortKey;

  public DeleteRange(PegasusTableInterface table, byte[] hashKey, int timeout) {
    super(hashKey, timeout);
    this.table = table;
  }

  public Boolean commitAndWait(int maxDeleteCount)
      throws PException, InterruptedException, ExecutionException, TimeoutException {
    this.scanOptions.noValue = true;
    ScannerWrapper<Boolean> scannerWrapper = new ScannerWrapper<>(table, this);

    if (timeout <= 0) timeout = ((PegasusTable) table).getDefaultTimeout();
    long deadlineTime = System.currentTimeMillis() + timeout;
    PegasusTable.ScanRangeResult result = scannerWrapper.hashScan(maxDeleteCount, timeout);

    if (System.currentTimeMillis() >= deadlineTime) {
      throw PException.timeout(
          ((PegasusTable) table).getMetaList(),
          ((PegasusTable) table).getTable().getTableName(),
          new PegasusTable.Request(hashKey),
          timeout,
          new TimeoutException());
    }

    List<byte[]> sortKeys = new ArrayList<>();
    int remainingTime = (int) (deadlineTime - System.currentTimeMillis());
    for (Pair<Pair<byte[], byte[]>, byte[]> pair : result.results) {
      remainingTime = (int) (deadlineTime - System.currentTimeMillis());
      sortKeys.add(pair.getKey().getValue());
      if (sortKeys.size() == this.scanOptions.batchSize) {
        nextSortKey = sortKeys.get(0);
        table
            .asyncMultiDel(hashKey, sortKeys, remainingTime)
            .get(remainingTime, TimeUnit.MILLISECONDS);
        if (remainingTime <= 0) {
          throw PException.timeout(
              ((PegasusTable) table).getMetaList(),
              ((PegasusTable) table).getTable().getTableName(),
              new PegasusTable.Request(hashKey),
              timeout,
              new TimeoutException());
        }
        sortKeys.clear();
      }
    }
    if (!sortKeys.isEmpty()) {
      table
          .asyncMultiDel(hashKey, sortKeys, remainingTime)
          .get(remainingTime, TimeUnit.MILLISECONDS);
      nextSortKey = null;
    }
    return result.allFetched;
  }

  public byte[] getNextSortKey() {
    return nextSortKey;
  }
}

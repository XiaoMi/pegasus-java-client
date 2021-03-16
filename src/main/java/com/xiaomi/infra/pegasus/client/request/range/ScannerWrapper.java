package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.*;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang3.tuple.Pair;

public class ScannerWrapper<Response> {
  private final Range<Response> request;
  private final PegasusTableInterface table;
  private final PegasusScannerInterface scanner;

  ScannerWrapper(PegasusTableInterface table, Range<Response> request) throws PException {
    this.table = table;
    this.request = request;
    this.scanner =
        table.getScanner(
            request.hashKey, request.startSortKey, request.stopSortKey, request.scanOptions);
  }

  PegasusTable.ScanRangeResult hashScan(int maxFetchCount, int timeout /*ms*/) throws PException {
    if (timeout <= 0) timeout = ((PegasusTable) table).getDefaultTimeout();
    long deadlineTime = System.currentTimeMillis() + timeout;

    PegasusTable.ScanRangeResult scanRangeResult = new PegasusTable.ScanRangeResult();
    scanRangeResult.allFetched = false;
    scanRangeResult.results = new ArrayList<>();
    if (System.currentTimeMillis() >= deadlineTime) {
      throw PException.timeout(
          ((PegasusTable) table).getMetaList(),
          ((PegasusTable) table).getTable().getTableName(),
          new PegasusTable.Request(request.hashKey),
          timeout,
          new TimeoutException());
    }

    Pair<Pair<byte[], byte[]>, byte[]> pair;
    while ((pair = scanner.next()) != null
        && (maxFetchCount <= 0 || scanRangeResult.results.size() < maxFetchCount)) {
      if (System.currentTimeMillis() >= deadlineTime) {
        throw PException.timeout(
            ((PegasusTable) table).getMetaList(),
            ((PegasusTable) table).getTable().getTableName(),
            new PegasusTable.Request(request.hashKey),
            timeout,
            new TimeoutException());
      }
      scanRangeResult.results.add(pair);
    }

    if (scanner.next() == null) {
      scanRangeResult.allFetched = true;
    }
    return scanRangeResult;
  }
}

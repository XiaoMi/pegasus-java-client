package com.xiaomi.infra.pegasus.client.request.range;

import static com.xiaomi.infra.pegasus.client.PegasusTableInterface.MultiGetResult;
import static com.xiaomi.infra.pegasus.client.PegasusTableInterface.MultiGetSortKeysResult;

import com.xiaomi.infra.pegasus.client.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang3.tuple.Pair;

public class ScannerWrapper<Response> {
  private final Range<Response> request;
  private final PegasusTableInterface table;
  private final PegasusScannerInterface scanner;

  public ScannerWrapper(Range<Response> request) throws PException {
    this.request = request;
    this.table = request.table;
    this.scanner =
        table.getScanner(
            request.hashKey, request.startSortKey, request.stopSortKey, request.scanOptions);
  }

  // TODO(jiashuo1) scanner.next()'s bug need be fixed, it will be put next pr
  public Result hashScan(int maxFetchCount) throws PException {
    long deadlineTime = System.currentTimeMillis() + request.timeout;

    Result scanRangeResult = new Result();
    scanRangeResult.allFetched = false;
    scanRangeResult.results = new ArrayList<>();
    if (System.currentTimeMillis() >= deadlineTime) {
      throw PException.timeout(
          ((PegasusTable) table).getMetaList(),
          ((PegasusTable) table).getTable().getTableName(),
          new PegasusTable.Request(request.hashKey),
          request.timeout,
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
            request.timeout,
            new TimeoutException());
      }
      scanRangeResult.results.add(pair);
    }

    if (scanner.next() == null) {
      scanRangeResult.allFetched = true;
    }
    return scanRangeResult;
  }

  public static class Result {
    public List<Pair<Pair<byte[], byte[]>, byte[]>> results;
    public boolean allFetched;

    public MultiGetResult convertMultiGetResult() {
      MultiGetResult multiGetResult = new MultiGetResult();
      if (results == null) {
        return multiGetResult;
      }
      multiGetResult.values = new ArrayList<>();
      for (Pair<Pair<byte[], byte[]>, byte[]> pair : results) {
        multiGetResult.values.add(Pair.of(pair.getLeft().getValue(), pair.getValue()));
      }
      multiGetResult.allFetched = allFetched;
      return multiGetResult;
    }

    public MultiGetSortKeysResult convertMultiGetSortKeysResult() {
      MultiGetSortKeysResult multiGetSortKeysResult = new MultiGetSortKeysResult();
      if (results == null) {
        return multiGetSortKeysResult;
      }
      multiGetSortKeysResult.keys = new ArrayList<>();
      for (Pair<Pair<byte[], byte[]>, byte[]> pair : results) {
        multiGetSortKeysResult.keys.add(pair.getLeft().getValue());
      }
      multiGetSortKeysResult.allFetched = allFetched;
      return multiGetSortKeysResult;
    }
  }
}

package com.xiaomi.infra.pegasus.client.request.range;

import static com.xiaomi.infra.pegasus.client.PegasusTableInterface.MultiGetResult;
import static com.xiaomi.infra.pegasus.client.PegasusTableInterface.MultiGetSortKeysResult;

import com.xiaomi.infra.pegasus.client.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class ScannerWrapper {
  private final PegasusScannerInterface scanner;

  public ScannerWrapper(PegasusScannerInterface scanner) throws PException {
    this.scanner = scanner;
  }

  // TODO(jiashuo1) scanner.next()'s bug need be fixed, it will be put next pr
  public Result hashScan(int maxFetchCount) throws PException {
    Result scanRangeResult = new Result();
    scanRangeResult.allFetched = false;
    scanRangeResult.results = new ArrayList<>();

    Pair<Pair<byte[], byte[]>, byte[]> pair;
    while ((pair = scanner.next()) != null
        && (maxFetchCount <= 0 || scanRangeResult.results.size() < maxFetchCount)) {
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

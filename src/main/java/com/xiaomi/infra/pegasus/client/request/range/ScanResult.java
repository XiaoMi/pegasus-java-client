package com.xiaomi.infra.pegasus.client.request.range;

import static com.xiaomi.infra.pegasus.client.PegasusTableInterface.MultiGetResult;
import static com.xiaomi.infra.pegasus.client.PegasusTableInterface.MultiGetSortKeysResult;

import com.xiaomi.infra.pegasus.client.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class ScanResult {
  public List<Pair<Pair<byte[], byte[]>, byte[]>> results;
  public boolean allFetched;
  public PException exception;

  public ScanResult() {
    this(new ArrayList<>(), false);
  }

  public ScanResult(List<Pair<Pair<byte[], byte[]>, byte[]>> results, boolean allFetched) {
    this.results = results;
    this.allFetched = allFetched;
  }

  public ScanResult(PException exception) {
    this.exception = exception;
  }

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

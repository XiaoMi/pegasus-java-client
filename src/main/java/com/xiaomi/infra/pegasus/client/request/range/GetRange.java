package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusScannerInterface;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import java.util.concurrent.*;
import org.apache.commons.lang3.tuple.Pair;

public class GetRange extends Range<ScanResult> {

  public GetRange(PegasusTableInterface table, byte[] hashKey, int timeout) {
    super(table, hashKey, timeout);
  }

  public ScanResult commitAndWait(int maxFetchCount) throws PException {
    ScanResult scanResult;
    try {
      scanResult =
          CompletableFuture.supplyAsync(
                  () -> {
                    ScanResult res = new ScanResult();
                    try {
                      PegasusScannerInterface scanner =
                          table.getScanner(hashKey, startSortKey, stopSortKey, scanOptions);
                      Pair<Pair<byte[], byte[]>, byte[]> pair;
                      while ((pair = scanner.next()) != null
                          && (maxFetchCount <= 0 || res.results.size() < maxFetchCount)) {
                        res.results.add(pair);
                      }
                      if (pair == null) {
                        res.allFetched = true;
                      }
                      return res;
                    } catch (PException e) {
                      return new ScanResult(e);
                    }
                  })
              .get(scanOptions.timeoutMillis, TimeUnit.MILLISECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new PException(e);
    }

    if (scanResult != null && scanResult.exception != null) {
      throw scanResult.exception;
    }
    return scanResult;
  }
}

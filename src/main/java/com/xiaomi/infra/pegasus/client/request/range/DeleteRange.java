package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.commons.lang3.tuple.Pair;

public class DeleteRange extends Range<Boolean> {

  public DeleteRange(PegasusTableInterface table, byte[] hashKey, int timeout) {
    super(table, hashKey, timeout);
  }

  public Boolean commitAndWait(int maxDeleteCount) throws PException {
    ScanResult scanResult = new ScanResult();
    try {
      scanResult =
          CompletableFuture.supplyAsync(
                  () -> {
                    try {
                      ScanOptions scanOptions = new ScanOptions();
                      scanOptions.noValue = true;
                      PegasusScannerInterface scanner =
                          table.getScanner(hashKey, startSortKey, stopSortKey, scanOptions);
                      List<byte[]> sortKeys = new ArrayList<>();
                      Pair<Pair<byte[], byte[]>, byte[]> pairs;
                      while ((pairs = scanner.next()) != null) {
                        sortKeys.add(pairs.getKey().getValue());
                        if (sortKeys.size() == scanOptions.batchSize) {
                          table.multiDel(hashKey, sortKeys, 0);
                          sortKeys.clear();
                        }
                      }
                      if (!sortKeys.isEmpty()) {
                        table.multiDel(hashKey, sortKeys, 0);
                      }
                    } catch (PException e) {
                      return new ScanResult(e);
                    }
                    return new ScanResult();
                  })
              .get(scanOptions.timeoutMillis, TimeUnit.MILLISECONDS);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      throw new PException(e);
    }

    if (scanResult.exception != null) {
      throw new PException(scanResult.exception);
    }
    return true;
  }
}

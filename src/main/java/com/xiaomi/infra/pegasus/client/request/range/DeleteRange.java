// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
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

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

// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.example;

import com.xiaomi.infra.pegasus.client.*;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/** full scan sample */
public class FullScanSample {

  public static void main(String[] args) throws PException, UnsupportedEncodingException {
    PegasusClientInterface client = PegasusClientFactory.createClient(ClientOptions.create());
    PegasusTableInterface table = client.openTable("temp");

    // Set up the scanners.
    ScanOptions scanOptions = new ScanOptions();
    scanOptions.batchSize = 20;
    // Values can be optimized out during scanning to reduce the workload.
    scanOptions.noValue = true;

    List<PegasusScannerInterface> scans = client.getUnorderedScanners("temp", 16, scanOptions);
    System.out.printf("opened %d scanners%n", scans.size());

    // Iterates sequentially.
    for (PegasusScannerInterface scan : scans) {
      int cnt = 0;
      long start = System.currentTimeMillis();
      while (true) {
        Pair<Pair<byte[], byte[]>, byte[]> pair = scan.next();
        if (null == pair) break;
        Pair<byte[], byte[]> keys = pair.getLeft();
        byte[] hashKey = keys.getLeft();
        byte[] sortKey = keys.getRight();
        cnt++;
        // get value
        byte[] value = table.get(hashKey, sortKey, 3000);
        System.out.printf(
            "hashKey = %s, sortKey = %s, value = %s%n",
            new String(hashKey, "UTF-8"), new String(sortKey, "UTF-8"), new String(value, "UTF-8"));
      }
      System.out.printf("sacn %d rows cost %d ms%n", cnt, System.currentTimeMillis() - start);
    }
    client.close();
  }
}

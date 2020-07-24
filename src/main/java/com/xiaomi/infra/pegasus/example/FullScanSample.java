// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.example;

import com.xiaomi.infra.pegasus.client.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Suppose we have a pegasus table storing user browsing history, and the requirement is to search
 * for the users that browsed one year ago. Given a table called user_history, which contains data
 * in schema：
 *
 * <p>---------------hashkey-------------- ------------sortkey----------- ----------value----------
 * ==============userId(string) ==================> timestamp(uint64) ========> web-content(string)
 * So we need to fully scan the table, find the hashkey that contains sortkey lower than
 * []bytes(oneYearAgoTs). Full scan example as follow
 */
public class FullScanSample {

  public static void main(String[] args) {
    try {
      searchHistoryOneYearAgo("user_history");
    } catch (PException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param tableName
   * @throws PException
   * @throws IOException
   */
  private static void searchHistoryOneYearAgo(String tableName) throws PException, IOException {
    PegasusClientInterface client = PegasusClientFactory.createClient(ClientOptions.create());

    // Set up the scanners.
    ScanOptions scanOptions = new ScanOptions();
    scanOptions.batchSize = 20;
    // Values can be optimized out during scanning to reduce the workload.
    scanOptions.noValue = true;

    List<PegasusScannerInterface> scans = client.getUnorderedScanners(tableName, 16, scanOptions);
    System.out.printf("opened %d scanners%n", scans.size());

    long oneYearAgo =
        LocalDateTime.now().plusYears(-1).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    // Iterates sequentially.
    for (PegasusScannerInterface scan : scans) {
      int cnt = 0;
      LocalDateTime start = LocalDateTime.now();
      while (true) {
        Pair<Pair<byte[], byte[]>, byte[]> pair = scan.next();
        if (null == pair) break;
        Pair<byte[], byte[]> keys = pair.getLeft();
        byte[] hashKey = keys.getLeft();
        byte[] sortKey = keys.getRight();
        if (sortKey.length == 13) {
          long res = Long.parseLong(new String(sortKey, "UTF-8"));
          if (res < oneYearAgo) {
            System.out.printf("hashKey = %s, sortKey = %d%n", new String(hashKey, "UTF-8"), res);
          }
        }
        cnt++;
        if (start.plusMinutes(1).isAfter(LocalDateTime.now())) {
          System.out.printf("scan 1 min, %d rows in total%n", cnt);
          start = LocalDateTime.now();
        }
      }
    }
    client.close();
  }
}

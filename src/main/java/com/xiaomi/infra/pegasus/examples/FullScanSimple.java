// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.examples;

import com.xiaomi.infra.pegasus.client.*;
import org.apache.commons.lang3.tuple.Pair;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class FullScanSimple {

    public static void main(String[] args) {
        try {
            searchHistoryOneYearAgo("temp");
        } catch (PException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Program exit!");
    }

    /**
     *
     * @param tableName
     * @throws PException
     * @throws IOException
     */
    private static void searchHistoryOneYearAgo(String tableName) throws PException, IOException {
        PegasusClientInterface client = PegasusClientFactory.createClient(ClientOptions.builder().build());

        // Set up the scanners.
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.batchSize = 20;
        scanOptions.noValue = true;
        // Values can be optimized out during scanning to reduce the workload.

        List<PegasusScannerInterface> scans = client.getUnorderedScanners(tableName, 16, scanOptions);
        System.out.printf("opened %d scanners\n", scans.size());

        long oneYearAgo = LocalDateTime.now().plusYears(-1).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("oneYearAgo = " + oneYearAgo);
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
                System.out.println("sortKey = " + new String(sortKey));
                if (sortKey.length == 13) {
                    long res = Long.valueOf(new String(sortKey));
                    System.out.println("res = " + res);
                    if (res < oneYearAgo) {
                        System.out.printf("hashKey = %s, sortKey = %d\n", new String(hashKey), res);
                    }
                }
                cnt++;
                if (start.plusMinutes(1).isAfter(LocalDateTime.now())) {
                    System.out.printf("scan 1 min, %d rows in total\n", cnt);
                    start = LocalDateTime.now();
                }
            }
        }
        client.close();
    }
}

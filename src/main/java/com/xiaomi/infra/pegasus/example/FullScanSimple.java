// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.example;

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
            searchHistoryOneYearAgo("user_history");
        } catch (PException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Program exit!");
    }

    private static void searchHistoryOneYearAgo(String tableName) throws PException, IOException {
        PegasusClientInterface client = PegasusClientFactory.createClient(ClientOptions.builder().build());

        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setBatchSize(20);
        scanOptions.setNoValue(true);

        List<PegasusScannerInterface> scans = client.getUnorderedScanners(tableName, 16, scanOptions);

        long oneYearAgo = LocalDateTime.now().plusYears(-1).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        for (PegasusScannerInterface scan : scans) {
            int cnt = 0;
            LocalDateTime start = LocalDateTime.now();
            while (true) {
                Pair<Pair<byte[], byte[]>, byte[]> pair = scan.next();
                if (null == pair) break;
                Pair<byte[], byte[]> keys = pair.getLeft();
                byte[] hashKey = keys.getLeft();
                byte[] sortKey = keys.getRight();
                if (sortKey.length == 8) {
                    long res = byteToLong(sortKey);
                    if (res < oneYearAgo) {
                        System.out.printf("hashKey = %s, sortKey = %d\n", hashKey.toString(), sortKey);
                    }
                }
                cnt++;
                if (start.plusMinutes(1).isAfter(LocalDateTime.now())) {
                    System.out.printf("scan 1 min, %d rows in total", cnt);
                    start = LocalDateTime.now();
                }
            }
        }
    }

    public static long byteToLong(byte[] b) throws IOException {
        ByteArrayInputStream bai = new ByteArrayInputStream(b);
        DataInputStream dis =new DataInputStream(bai);
        return dis.readLong();
    }
}

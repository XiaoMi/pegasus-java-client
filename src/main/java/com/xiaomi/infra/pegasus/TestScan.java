package com.xiaomi.infra.pegasus;

import static com.xiaomi.infra.pegasus.client.FilterType.*;

import com.xiaomi.infra.pegasus.client.*;
import org.apache.commons.lang3.tuple.Pair;

import java.time.Duration;

public class TestScan {
    private static final String metaServerAddrs = "127.0.0.1:34601,127.0.0.1:34602,127.0.0.1:34603";
    private static final String tableName = "temp";

    private static final String hashKey = "scan";
    private static final String startSortKey = "";
    private static final String stopSortKey = "";

    private static final int batchSize = 10;
    private static final int scanTimeoutMillis = 10000;
    private static final Boolean startInclusive = true;
    private static final Boolean stopInclusive = true;
    private static final FilterType hashKeyFilterType = FT_NO_FILTER;
    private static final String hashKeyFilterPattern = "";
    private static final FilterType sortKeyFilterType = FT_NO_FILTER;
    private static final String sortKeyFilterPattern = "";
    private static final Boolean noValue = false;

    private static PegasusClientInterface client;//如果维护的table = client.openTable为全局变量，请务必确保client实例也是全局变量

    public static void main(String[] args) throws InterruptedException {
        ClientOptions clientOptions =
                ClientOptions.builder()
                        .metaServers(metaServerAddrs)
                        .operationTimeout(Duration.ofMillis(1000))
                        .build();
        // 1.也可以使用PegasusClientFactory.getSingletonClient(String configPath)创建单例
        // 2.如不希望构建单例，可以使用createClient(String configPath)或者createClient(ClientOptions options)创建
        try {
            client = PegasusClientFactory.getSingletonClient(clientOptions);
        } catch (PException e) {
            e.printStackTrace();
        }

        ScanOptions options = new ScanOptions();
        options.timeoutMillis = 100;
        options.batchSize = batchSize;
        options.startInclusive = startInclusive;
        options.stopInclusive = stopInclusive;
        options.hashKeyFilterType = hashKeyFilterType;
        options.hashKeyFilterPattern = hashKeyFilterPattern.getBytes();
        options.sortKeyFilterType = sortKeyFilterType;
        options.sortKeyFilterPattern = sortKeyFilterPattern.getBytes();
        options.noValue = noValue;

        PegasusScannerInterface scanner = null;
        try {
            scanner = client.getScanner(tableName, hashKey.getBytes(),
                    startSortKey.getBytes(), stopSortKey.getBytes(), options);
        } catch (PException e) {
            e.printStackTrace();
        }
        System.out.println("hash scan successfully:");
        Pair<Pair<byte[], byte[]>, byte[]> result  = null;
        try {
            result = scanner.next();
        } catch (PException e) {
            e.printStackTrace();
        }
        while (result != null) {
            try {
                result = scanner.next();
            } catch (PException e) {
                System.out.println(e.getMessage());
            }

            System.out.printf("hashKey=\"%s\", sortKey=\"%s\", value=\"%s\"\n",
                    new String(result.getKey().getKey()), new String(result.getKey().getValue()), new String(result.getValue()));
            Thread.sleep(5000);
        }
    }
}
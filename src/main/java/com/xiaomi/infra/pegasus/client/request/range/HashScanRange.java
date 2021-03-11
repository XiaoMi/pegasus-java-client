package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTable;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import com.xiaomi.infra.pegasus.client.ScanOptions;

public class HashScanRange  {
    public PegasusTableInterface table;

    private ScanOptions scanOptions;
    private byte[] hashKey;
    private byte[] startSortKey;
    private byte[] stopSortKey;
    private int maxFetchCount;
    private int timeout;

    public HashScanRange(PegasusTableInterface table, ScanOptions scanOptions, byte[] hashKey, byte[] startSortKey, byte[] stopSortKey, int maxFetchCount, int timeout) {
        this.table = table;
        this.scanOptions = scanOptions;
        this.startSortKey = startSortKey;
        this.hashKey = hashKey;
        this.stopSortKey = stopSortKey;
        this.maxFetchCount = maxFetchCount;
        this.timeout = timeout;
    }

    public PegasusTable.ScanRangeResult commitAndWait() throws PException {
        ScannerWrapper scannerWrapper = new ScannerWrapper(table);
        return scannerWrapper.hashScan(hashKey, startSortKey,stopSortKey,scanOptions, maxFetchCount, timeout);
    }
}

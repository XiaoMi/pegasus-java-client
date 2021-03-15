package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.ScanOptions;

public class Scan {
    public byte[] hashKey;
    public int timeout;

    public ScanOptions scanOptions = new ScanOptions();
    public byte[] startSortKey;
    public byte[] stopSortKey;

    public Scan(byte[] hashKey, int timeout) {
        this.hashKey = hashKey;
        this.timeout = timeout;
    }

    public Scan withOptions(ScanOptions scanOptions) {
        this.scanOptions = scanOptions;
        return this;
    }

    public Scan withStartSortKey(byte[] startSortKey) {
        this.startSortKey = startSortKey;
        return this;
    }

    public void withStopSortKey(byte[] stopSortKey) {
        this.stopSortKey = stopSortKey;
    }
}

package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTable;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import com.xiaomi.infra.pegasus.client.ScanOptions;

public class GetRange extends Scan{
    public PegasusTableInterface table;
    public int maxFetchCount;

    public GetRange(PegasusTableInterface table, byte[] hashKey, int maxFetchCount, int timeout) {
        super(hashKey, timeout);
        this.table = table;
        this.maxFetchCount = maxFetchCount;
    }

    public PegasusTable.ScanRangeResult commitAndWait() throws PException {
        ScannerWrapper scannerWrapper = new ScannerWrapper(table);
        return scannerWrapper.hashScan(hashKey, startSortKey, stopSortKey, scanOptions, maxFetchCount, timeout);
    }
}

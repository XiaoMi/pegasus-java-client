package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.*;
import com.xiaomi.infra.pegasus.rpc.Table;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class ScannerWrapper {
    private final PegasusTableInterface table;

    public ScannerWrapper(PegasusTableInterface table) {
        this.table = table;
    }

   PegasusTable.ScanRangeResult hashScan(
           byte[] hashKey,
           byte[] startSortKey,
           byte[] stopSortKey,
           ScanOptions options,
           int maxFetchCount,
           int timeout /*ms*/)
           throws PException {
       if (timeout <= 0) timeout = ((PegasusTable)table).getDefaultTimeout();
       long deadlineTime = System.currentTimeMillis() + timeout;

       PegasusScannerInterface pegasusScanner =
               table.getScanner(hashKey, startSortKey, stopSortKey, options);
       PegasusTable.ScanRangeResult scanRangeResult = new PegasusTable.ScanRangeResult();
       scanRangeResult.allFetched = false;
       scanRangeResult.results = new ArrayList<>();
       if (System.currentTimeMillis() >= deadlineTime) {
           throw PException.timeout(
                   ((PegasusTable)table).getMetaList(),
                   ((PegasusTable)table).getTable().getTableName(),
                   new PegasusTable.Request(hashKey), timeout, new TimeoutException());
       }

       Pair<Pair<byte[], byte[]>, byte[]> pair;
       while ((pair = pegasusScanner.next()) != null
               && (maxFetchCount <= 0 || scanRangeResult.results.size() < maxFetchCount)) {
           if (System.currentTimeMillis() >= deadlineTime) {
               throw PException.timeout(
                       ((PegasusTable)table).getMetaList(),
                       ((PegasusTable)table).getTable().getTableName(),
                       new PegasusTable.Request(hashKey), timeout, new TimeoutException());
           }
           scanRangeResult.results.add(pair);
       }

       if (pegasusScanner.next() == null) {
           scanRangeResult.allFetched = true;
       }
       return scanRangeResult;
   }
}

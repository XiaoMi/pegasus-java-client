package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;

public class BatchMultiSet extends Batch<MultiSet, Void>{

    public BatchMultiSet(PegasusTableInterface table, int timeout) {
        super(table, timeout);
    }

    @Override
    public Future<Void> asyncCommit(MultiSet multiSet) {
        return table.asyncMultiSet(multiSet.hashKey, multiSet.values, multiSet.ttlSeconds,timeout);
    }
}

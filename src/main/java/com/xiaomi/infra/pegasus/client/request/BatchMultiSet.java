package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.util.List;

public class BatchMultiSet extends Batch<MultiSet, Void>{

    public BatchMultiSet(List<MultiSet> multiSets) {
        super(multiSets);
    }

    public BatchMultiSet(PegasusTableInterface table,
        List<MultiSet> multiSets, int timeout) {
        super(table, multiSets, timeout);
    }

    @Override
    public Future<Void> asyncCommit(MultiSet multiSet) {
        return table.asyncMultiSet(multiSet.hashKey, multiSet.values, multiSet.ttlSeconds,timeout);
    }
}

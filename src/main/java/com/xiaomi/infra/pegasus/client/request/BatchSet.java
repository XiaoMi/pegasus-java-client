package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;

class BatchSet extends Batch<Set, Void> {
    public BatchSet(PegasusTableInterface table, int timeout) {
        super(table, timeout);
    }

    @Override
    public Future<Void> asyncCommit(Set set) {
        return table.asyncSet(set.hashKey, set.sortKey, set.value, timeout);
    }
}

package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.util.List;

public class BatchSet extends Batch<Set, Void> {

    public BatchSet(List<Set> sets) {
        super(sets);
    }

    public BatchSet(PegasusTableInterface table,
        List<Set> sets, int timeout) {
        super(table, sets, timeout);
    }

    @Override
    public Future<Void> asyncCommit(Set set) {
        return table.asyncSet(set.hashKey, set.sortKey, set.value, set.ttlSeconds, timeout);
    }
}

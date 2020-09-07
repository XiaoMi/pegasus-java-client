package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.util.List;

public class BatchDelete extends Batch<Delete, Void>{

    public BatchDelete(List<Delete> deletes) {
        super(deletes);
    }

    public BatchDelete(PegasusTableInterface table,
        List<Delete> deletes, int timeout) {
        super(table, deletes, timeout);
    }

    @Override
    public Future<Void> asyncCommit(Delete delete) {
        return table.asyncDel(delete.hashKey, delete.sortKey,timeout);
    }
}

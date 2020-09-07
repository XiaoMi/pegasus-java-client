package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.util.List;

public class BatchMultiDelete extends Batch<MultiDelete, Void> {

    public BatchMultiDelete(
        List<MultiDelete> multiDeletes) {
        super(multiDeletes);
    }

    public BatchMultiDelete(PegasusTableInterface table,
        List<MultiDelete> multiDeletes, int timeout) {
        super(table, multiDeletes, timeout);
    }

    @Override
    public Future<Void> asyncCommit(MultiDelete multiDelete) {
        return table.asyncMultiDel(multiDelete.hashKey, multiDelete.sortKeys, timeout);
    }
}

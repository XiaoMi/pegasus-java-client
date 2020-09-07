package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface.MultiGetResult;
import io.netty.util.concurrent.Future;
import java.util.List;

public class BatchMultiGet extends Batch<MultiGet, MultiGetResult> {

    public BatchMultiGet(List<MultiGet> multiGets) {
        super(multiGets);
    }

    public BatchMultiGet(PegasusTableInterface table,
        List<MultiGet> multiGets, int timeout) {
        super(table, multiGets, timeout);
    }

    @Override
    public Future<MultiGetResult> asyncCommit(MultiGet multiGet) {
        return table.asyncMultiGet(multiGet.hashKey, multiGet.sortKeys,timeout);
    }
}

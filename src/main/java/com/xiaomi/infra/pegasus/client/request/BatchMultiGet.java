package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface.MultiGetResult;
import io.netty.util.concurrent.Future;

public class BatchMultiGet extends Batch<MultiGet, MultiGetResult> {

    public BatchMultiGet(PegasusTableInterface table, int timeout) {
        super(table, timeout);
    }

    @Override
    Future<MultiGetResult> asyncCommit(MultiGet multiGet) {
        return table.asyncMultiGet(multiGet.hashKey, multiGet.sortKeys,timeout);
    }
}

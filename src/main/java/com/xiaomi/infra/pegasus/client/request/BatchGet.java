package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;

public class BatchGet extends Batch<Get, byte[]> {
    public BatchGet(PegasusTableInterface table, int timeout) {
        super(table, timeout);
    }

    @Override
    public Future<byte[]> asyncCommit(Get get) {
        return table.asyncGet(get.hashKey, get.sortKey, timeout);
    }
}
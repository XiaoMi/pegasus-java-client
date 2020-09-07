package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.util.List;

public class BatchGet extends Batch<Get, byte[]> {

    public BatchGet(List<Get> gets) {
        super(gets);
    }

    public BatchGet(PegasusTableInterface table,
        List<Get> gets, int timeout) {
        super(table, gets, timeout);
    }

    @Override
    public Future<byte[]> asyncCommit(Get get) {
        return table.asyncGet(get.hashKey, get.sortKey, timeout);
    }
}
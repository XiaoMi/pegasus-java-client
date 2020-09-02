package com.xiaomi.infra.pegasus;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusClientFactory;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface.MultiGetResult;
import com.xiaomi.infra.pegasus.client.request.Batch;
import com.xiaomi.infra.pegasus.client.request.BatchGet;
import com.xiaomi.infra.pegasus.client.request.Get;
import com.xiaomi.infra.pegasus.client.request.MultiGet;
import io.netty.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;

public class PegasusTest {

    public static void main(String[] args) throws PException {
        PegasusTableInterface table = PegasusClientFactory.getSingletonClient().openTable("temp");
        Batch<Get,byte[]> batch = new BatchGet(table, 1000);
        List<Get> requests = new ArrayList<>();
        List<byte[]> responses = new ArrayList<>();
        requests.add(new Get("hashKey1".getBytes(),"sortKey1".getBytes()));
        requests.add(new Get("hashKey2".getBytes(),"sortKey2".getBytes()));
        batch.commit(requests, responses);


        Batch<MultiGet, MultiGetResult> multiGetbatch =  new Batch<MultiGet, MultiGetResult>(table,1000) {
            @Override
            public Future<MultiGetResult> asyncCommit(MultiGet multiGet) {
                return table.asyncMultiGet(multiGet.hashKey, multiGet.sortKeys,timeout);
            }
        };

        List<MultiGet> multiGetsRequests = new ArrayList<>();
        List<MultiGetResult> multiGetsResponses = new ArrayList<>();
        multiGetbatch.commit(multiGetsRequests, multiGetsResponses);
    }
}

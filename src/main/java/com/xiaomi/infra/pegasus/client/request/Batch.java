// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.FutureGroup;
import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public abstract class Batch<Request, Response> {

    private final List<Request> requests;
    public PegasusTableInterface table;
    public int timeout;

    public Batch(List<Request> requests) {
        assert  !requests.isEmpty() : "requests mustn't be empty";
        this.requests = requests;
    }

    public Batch(PegasusTableInterface table, List<Request> requests, int timeout) {
        this.table = table;
        this.requests = requests;
        this.timeout = timeout;
    }

    public void commit() throws PException {
        asyncCommit().waitAllCompleteOrOneFail(timeout);
    }

    public void commit(List<Response> responses) throws PException {
        asyncCommit().waitAllCompleteOrOneFail(responses, timeout);
    }

    public void commitWaitAllComplete(List<Pair<PException,Response>> responses) throws PException {
        asyncCommit().waitAllComplete(responses, timeout);
    }

    private FutureGroup<Response> asyncCommit(){
        assert (table != null);
        FutureGroup<Response> futureGroup = new FutureGroup<>(requests.size());
        for (Request request : requests) {
            futureGroup.add(asyncCommit(request));
        }
        return futureGroup;
    }


    public Batch<Request,Response> setTable(PegasusTableInterface table){
        this.table = table;
        return this;
    }

    public Batch<Request,Response> setTimeout(int timeout){
        this.timeout = timeout;
        return this;
    }

    public abstract Future<Response> asyncCommit(Request request);
}

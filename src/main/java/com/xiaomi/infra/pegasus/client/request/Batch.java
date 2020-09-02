package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.FutureGroup;
import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public abstract class Batch<Request, Response> {

    public final PegasusTableInterface table;
    public final int timeout;

    private FutureGroup<Response> futureGroup;

    public Batch(PegasusTableInterface table, int timeout) {
        this.table = table;
        this.timeout = timeout;
    }

    public void commit(List<Request> requests) throws PException {
        assert (!requests.isEmpty());
        asyncCommit(requests).waitAllCompleteOrOneFail(null, timeout);
    }

    public void commit(List<Request> requests, List<Response> responses) throws PException {
        assert (!requests.isEmpty());
        asyncCommit(requests).waitAllCompleteOrOneFail(responses, timeout);
    }

    public void commitWaitAllComplete(List<Request> requests, List<Pair<PException,Response>> responses) throws PException {
        assert (!requests.isEmpty());
        asyncCommit(requests).waitAllcomplete(responses, timeout);
    }

    private FutureGroup<Response> asyncCommit(List<Request> requests){
        futureGroup = new FutureGroup<>(requests.size());
        for (Request request : requests) {
            futureGroup.add(asyncCommit(request));
        }
        return futureGroup;
    }

    public abstract Future<Response> asyncCommit(Request request);
}

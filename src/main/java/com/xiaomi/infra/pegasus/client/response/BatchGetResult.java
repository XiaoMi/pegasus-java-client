package com.xiaomi.infra.pegasus.client.response;

import com.xiaomi.infra.pegasus.client.PException;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class BatchGetResult {
    public List<Pair<PException, byte[]>> results;

    public void add(PException pe, byte[] value) {
        results.add(Pair.of(pe, value));
    }
}

package com.xiaomi.infra.pegasus.client.request;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class MultiSet {
    public final byte[] hashKey;
    public final List<Pair<byte[], byte[]>> values;

    public int ttlSeconds;

    public MultiSet(byte[] hashKey) {
        this(hashKey, new ArrayList<>());
    }

    public MultiSet(byte[] hashKey,
        List<Pair<byte[], byte[]>> values) {
        assert (hashKey != null && hashKey.length > 0 && hashKey.length < 0xFFFF && values != null);
        this.hashKey = hashKey;
        this.values = values;
        this.ttlSeconds = 0;
    }

    public MultiSet add(byte[] sortKey, byte[] value) {
        values.add(Pair.of(sortKey, value));
        return this;
    }

    public MultiSet withTTLSeconds(int ttlSeconds){
        assert ttlSeconds > 0;
        this.ttlSeconds = ttlSeconds;
        return this;
    }
}

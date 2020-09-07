package com.xiaomi.infra.pegasus.client.request;

import java.util.ArrayList;
import java.util.List;

public class MultiGet {
    public byte[] hashKey;
    public List<byte[]> sortKeys;

    public MultiGet(byte[] hashKey) {
        this(hashKey, new ArrayList<>());
    }

    public MultiGet(byte[] hashKey, List<byte[]> sortKeys) {
        assert sortKeys != null;
        this.hashKey = hashKey;
        this.sortKeys = sortKeys;
    }

    public MultiGet add(byte[] sortKey){
        sortKeys.add(sortKey);
        return this;
    }
}

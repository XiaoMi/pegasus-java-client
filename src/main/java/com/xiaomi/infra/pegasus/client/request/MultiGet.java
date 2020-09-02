package com.xiaomi.infra.pegasus.client.request;

import java.util.List;

public class MultiGet {
    public byte[] hashKey;
    public List<byte[]> sortKeys;

    public MultiGet(byte[] hashKey, List<byte[]> sortKeys) {
        this.hashKey = hashKey;
        this.sortKeys = sortKeys;
    }
}

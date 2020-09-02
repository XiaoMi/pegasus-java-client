package com.xiaomi.infra.pegasus.client.request;

import java.io.Serializable;

public class Set implements Serializable {
    public boolean response;

    public byte[] hashKey;
    public byte[] sortKey;
    public byte[] value;
    public int ttlSeconds; // 0 means no ttl

    public Set(byte[] hashKey, byte[] sortKey, byte[] value) {
        this(hashKey, sortKey, value, 0);
    }

    public Set(byte[] hashKey, byte[] sortKey, byte[] value, int ttlSeconds) {
        assert (value != null && ttlSeconds >= 0);
        this.hashKey = hashKey;
        this.sortKey = sortKey;
        this.value = value;
        this.ttlSeconds = ttlSeconds;
    }
}

package com.xiaomi.infra.pegasus.client.request;

public class Increment extends Key{
    int increment = 1;
    int ttlSeconds = 0;

    public Increment(byte[] hashKey, byte[] sortKey) {
        super(hashKey, sortKey);
    }

    public Increment(byte[] hashKey, byte[] sortKey, int increment, int ttlSeconds) {
        super(hashKey, sortKey);
        this.ttlSeconds = ttlSeconds;
        this.increment = increment;
    }
}

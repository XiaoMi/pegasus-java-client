package com.xiaomi.infra.pegasus.client.request;

public class Delete {
    public final byte[] hashKey;
    public final byte[] sortKey;

    public Delete(byte[] hashKey) {
        this(hashKey, null);
    }

    public Delete(byte[] hashKey, byte[] sortKey) {
        this.hashKey = hashKey;
        this.sortKey = sortKey;
    }
}

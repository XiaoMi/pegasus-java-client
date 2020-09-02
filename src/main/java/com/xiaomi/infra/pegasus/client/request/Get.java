package com.xiaomi.infra.pegasus.client.request;

public class Get  {
    public byte[] hashKey;
    public byte[] sortKey;

    public Get(byte[] hashKey) {
        this.hashKey = hashKey;
    }

    public Get(byte[] hashKey, byte[] sortKey) {
        this.hashKey = hashKey;
        this.sortKey = sortKey;
    }

}

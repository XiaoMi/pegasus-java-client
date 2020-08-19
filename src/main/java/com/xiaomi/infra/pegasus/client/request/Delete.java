package com.xiaomi.infra.pegasus.client.request;

public class Delete extends Key{

    public Delete(byte[] hashKey) {
        super(hashKey);
    }

    public Delete(byte[] hashKey, byte[] sortKey) {
        super(hashKey, sortKey);
    }
}

package com.xiaomi.infra.pegasus.client.request;

import java.io.Serializable;

public class Set implements Serializable {
    public byte[] hashKey;
    public byte[] sortKey;
    public byte[] value;
    public int ttlSeconds;

    public Set(byte[] hashKey, byte[] sortKey, byte[] value) {
        this.hashKey = hashKey;
        this.sortKey = sortKey;
        this.value = value;
        this.ttlSeconds = 0;
    }

    public Set withTTLSeconds(int ttlSeconds){
        assert ttlSeconds > 0;
        this.ttlSeconds = ttlSeconds;
        return this;
   }
}

// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Set implements Serializable {
    public byte[] hashKey;
    public byte[] sortKey;
    @NotNull
    public byte[] value ;
    @Min(0)
    public int ttlSeconds = 0; // 0 means no ttl

    public Set() {}

    public Set(byte[] hashKey, byte[] sortKey, byte[] value, int ttlSeconds) {
        this.hashKey = hashKey;
        this.sortKey = sortKey;
        this.value = value;
        this.ttlSeconds = ttlSeconds;
    }

    public Set(byte[] hashKey, byte[] sortKey, byte[] value) {
        this.hashKey = hashKey;
        this.sortKey = sortKey;
        this.value = value;
        this.ttlSeconds = 0;
    }
}

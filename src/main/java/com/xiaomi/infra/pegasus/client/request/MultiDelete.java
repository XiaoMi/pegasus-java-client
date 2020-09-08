// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import java.util.ArrayList;
import java.util.List;

public class MultiDelete {
    public final byte[] hashKey;
    public final List<byte[]> sortKeys;

    public MultiDelete(byte[] hashKey) {
        this(hashKey, new ArrayList<>());
    }

    public MultiDelete(byte[] hashKey, List<byte[]> sortKeys) {
        assert sortKeys != null;
        this.hashKey = hashKey;
        this.sortKeys = sortKeys;
    }

    public MultiDelete add(byte[] sortKey){
        sortKeys.add(sortKey);
        return this;
    }

}

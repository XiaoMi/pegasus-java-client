// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.util.List;

public class BatchSet extends Batch<Set, Void> {

    public BatchSet(List<Set> sets) {
        super(sets);
    }

    public BatchSet(PegasusTableInterface table,
        List<Set> sets, int timeout) {
        super(table, sets, timeout);
    }

    @Override
    public Future<Void> asyncCommit(Set set) {
        return table.asyncSet(set.hashKey, set.sortKey, set.value, set.ttlSeconds, timeout);
    }
}

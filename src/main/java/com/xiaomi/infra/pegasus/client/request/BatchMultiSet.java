// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.util.List;

public class BatchMultiSet extends Batch<MultiSet, Void>{

    public BatchMultiSet(List<MultiSet> multiSets) {
        super(multiSets);
    }

    public BatchMultiSet(PegasusTableInterface table,
        List<MultiSet> multiSets, int timeout) {
        super(table, multiSets, timeout);
    }

    @Override
    public Future<Void> asyncCommit(MultiSet multiSet) {
        return table.asyncMultiSet(multiSet.hashKey, multiSet.values, multiSet.ttlSeconds,timeout);
    }
}

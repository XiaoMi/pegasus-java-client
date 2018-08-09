// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

import com.xiaomi.infra.pegasus.apps.mutate;
import com.xiaomi.infra.pegasus.apps.mutate_operation;
import com.xiaomi.infra.pegasus.base.blob;
import com.xiaomi.infra.pegasus.tools.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author huangwei
 * <p>
 * Mutations in Check-and-mutate.
 * The mutations in the same object take the same time.
 */

public class Mutations {
    private final List<mutate> muList;
    private final int currentTime;

    Mutations() {
        muList = new ArrayList<mutate>();
        currentTime = (int) Tools.epoch_now();
    }

    public void set(byte[] sortKey, byte[] value, int ttlSeconds) {
        blob sortKeyBlob = (sortKey == null ? null : new blob(sortKey));
        blob valueBlob = (value == null ? null : new blob(value));
        int expireSeconds = (ttlSeconds == 0 ? 0 : ttlSeconds + currentTime);
        muList.add(new mutate(mutate_operation.MO_PUT, sortKeyBlob, valueBlob, expireSeconds));
    }

    public void set(byte[] sortKey, byte[] value) {
        blob sortKeyBlob = (sortKey == null ? null : new blob(sortKey));
        blob valueBlob = (value == null ? null : new blob(value));
        muList.add(new mutate(mutate_operation.MO_PUT, sortKeyBlob, valueBlob, 0));
    }

    public void del(byte[] sortKey) {
        blob sortKeyBlob = (sortKey == null ? null : new blob(sortKey));
        muList.add(new mutate(mutate_operation.MO_DELETE, sortKeyBlob, null, 0));
    }

    List<mutate> getMutations() {
        return Collections.unmodifiableList(muList);
    }

    boolean isEmpty() {
        return muList.isEmpty();
    }
}

// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

/**
 * @author qinzuoyan
 *
 * Check type.
 */
public enum CheckType {
    CT_NO_CHECK(0),
    CT_VALUE_NOT_EXIST(1),
    CT_VALUE_EXIST(2),
    CT_VALUE_NOT_EMPTY(3),
    CT_VALUE_EQUAL(4),
    CT_VALUE_MATCH_ANYWHERE(5),
    CT_VALUE_MATCH_PREFIX(6),
    CT_VALUE_MATCH_POSTFIX(7);

    private final int value;

    private CheckType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

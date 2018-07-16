// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

/**
 * @author qinzuoyan
 *
 * Check-and-set options.
 */
public class CheckAndSetOptions {
    int setValueTTLSeconds = 0; // time to live in seconds of the set value, 0 means no ttl.
    boolean returnCheckValue = false; // if return the check value in results.

    public CheckAndSetOptions() {}

    public CheckAndSetOptions(CheckAndSetOptions o) {
        setValueTTLSeconds = o.setValueTTLSeconds;
        returnCheckValue = o.returnCheckValue;
    }
}

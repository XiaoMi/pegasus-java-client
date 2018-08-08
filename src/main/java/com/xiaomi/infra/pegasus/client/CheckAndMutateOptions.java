// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

/**
 * @author huangwei
 *
 * Check-and-mutate options.
 */
public class CheckAndMutateOptions {
    //public int setValueTTLSeconds = 0; // time to live in seconds of the set value, 0 means no ttl. // TODO HW move to Mutate class
    public boolean returnCheckValue = false; // if return the check value in results.

    public CheckAndMutateOptions() {}

    public CheckAndMutateOptions(CheckAndMutateOptions o) {
        //setValueTTLSeconds = o.setValueTTLSeconds;
        returnCheckValue = o.returnCheckValue;
    }
}

// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

/**
 * @author qinzuoyan
 *
 * Check-and-set results.
 */
public class CheckAndSetResults {
    boolean setSucceed = false; // if set value succeed.
    boolean checkValueReturned = false; // if the check value is returned.
    boolean checkValueExist = false; // if the check value is exist; can be used only when checkValueReturned is true.
    byte[] checkValue = null; // the check value; can be used only when checkValueExist is true.

    public CheckAndSetResults() {}

    public CheckAndSetResults(CheckAndSetResults o) {
        setSucceed = o.setSucceed;
        checkValueReturned = o.checkValueReturned;
        checkValueExist = o.checkValueExist;
        checkValue = o.checkValue;
    }
}

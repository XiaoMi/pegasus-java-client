// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

/**
 * @author qinzuoyan
 */

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mi on 18-7-17.
 */
public class TestCheckAndSet {
    @Test
    public void testValueNotExist() throws PException {
        PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
        String tableName = "temp";

        byte[] hashKey = "check_and_set_java_test_value_not_exist".getBytes();
        try {
            client.del(tableName, hashKey, "k1".getBytes());

            CheckAndSetOptions options = new CheckAndSetOptions();
            PegasusTableInterface.CheckAndSetResult result;
            byte[] value;

            options.returnCheckValue = true;
            result = client.checkAndSet(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_NOT_EXIST, "".getBytes(),
                    "k1".getBytes(), "v1".getBytes(), options);
            Assert.assertTrue(result.setSucceed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertFalse(result.checkValueExist);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v1".getBytes(), value);

            client.del(tableName, hashKey, "k1".getBytes());
        }
        catch (PException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

        PegasusClientFactory.closeSingletonClient();
    }
}

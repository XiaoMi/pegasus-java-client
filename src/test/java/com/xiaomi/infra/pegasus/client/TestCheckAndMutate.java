// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

/*
  @author huangwei
 */

import com.xiaomi.infra.pegasus.apps.mutate_operation;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestCheckAndMutate {
    @Test
    public void testValueNotExist() throws PException {
        PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
        String tableName = "temp";
        byte[] hashKey = "check_and_mutate_java_test_value_not_exist".getBytes();

        try {
            client.del(tableName, hashKey, "k1".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k1".getBytes(), "v1".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_NOT_EXIST, null,
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertFalse(result.checkValueExist);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v1".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_NOT_EXIST, null,
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v1".getBytes(), value);

            options.returnCheckValue = false;
            mutates.get(0).value = "v1".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_NOT_EXIST, null,
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertFalse(result.checkValueReturned);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v1".getBytes(), value);

            client.del(tableName, hashKey, "k1".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {
            client.del(tableName, hashKey, "k2".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k2".getBytes(), "".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k2".getBytes(), CheckType.CT_VALUE_NOT_EXIST, null,
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertFalse(result.checkValueExist);
            value = client.get(tableName, hashKey, "k2".getBytes());
            Assert.assertArrayEquals("".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k2".getBytes(), CheckType.CT_VALUE_NOT_EXIST, null,
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k2".getBytes());
            Assert.assertArrayEquals("".getBytes(), value);

            client.del(tableName, hashKey, "k1".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {
            client.del(tableName, hashKey, "k3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k4".getBytes(), "v4".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k3".getBytes(), CheckType.CT_VALUE_NOT_EXIST, null,
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertFalse(result.checkValueExist);
            value = client.get(tableName, hashKey, "k4".getBytes());
            Assert.assertArrayEquals("v4".getBytes(), value);

            client.del(tableName, hashKey, "k3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        PegasusClientFactory.closeSingletonClient();
    }

    @Test
    public void testValueExist() throws PException {
        PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
        String tableName = "temp";
        byte[] hashKey = "check_and_mutate_java_test_value_exist".getBytes();

        try {
            client.del(tableName, hashKey, "k1".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k1".getBytes(), "v1".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_EXIST, null,
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertFalse(result.checkValueExist);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertNull(value);

            client.set(tableName, hashKey, "k1".getBytes(), "".getBytes());

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_EXIST, null,
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v1".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_EXIST, null,
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            client.del(tableName, hashKey, "k1".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {
            client.set(tableName, hashKey, "k3".getBytes(), "v3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k4".getBytes(), "v4".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k3".getBytes(), CheckType.CT_VALUE_EXIST, null,
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v3".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k4".getBytes());
            Assert.assertArrayEquals("v4".getBytes(), value);

            client.del(tableName, hashKey, "k3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        PegasusClientFactory.closeSingletonClient();
    }

    @Test
    public void testValueNotEmpty() throws PException {
        PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
        String tableName = "temp";
        byte[] hashKey = "check_and_mutate_java_test_value_not_empty".getBytes();

        try {
            client.del(tableName, hashKey, "k1".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k1".getBytes(), "v1".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_NOT_EMPTY, null,
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertFalse(result.checkValueExist);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertNull(value);

            client.set(tableName, hashKey, "k1".getBytes(), "".getBytes());

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_NOT_EMPTY, null,
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("".getBytes(), value);

            client.set(tableName, hashKey, "k1".getBytes(), "v1".getBytes());

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_NOT_EMPTY, null,
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            client.del(tableName, hashKey, "k1".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {
            client.set(tableName, hashKey, "k3".getBytes(), "v3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k4".getBytes(), "v4".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k3".getBytes(), CheckType.CT_VALUE_NOT_EMPTY, null,
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v3".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k4".getBytes());
            Assert.assertArrayEquals("v4".getBytes(), value);

            client.del(tableName, hashKey, "k3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        PegasusClientFactory.closeSingletonClient();
    }

    @Test
    public void testMatchAnywhere() throws PException {
        PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
        String tableName = "temp";
        byte[] hashKey = "check_and_mutate_java_test_value_match_anywhere".getBytes();

        try {
            client.del(tableName, hashKey, "k1".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k1".getBytes(), "v1".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_ANYWHERE, "v".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertFalse(result.checkValueExist);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertNull(value);

            client.set(tableName, hashKey, "k1".getBytes(), "".getBytes());

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_ANYWHERE, "v".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("".getBytes(), value);

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_ANYWHERE, "".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v1".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_ANYWHERE, "".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v111v".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_ANYWHERE, "2".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v111v".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_ANYWHERE, "111".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v111v".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v3".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_ANYWHERE, "y".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_ANYWHERE, "v2v".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_ANYWHERE, "v2".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v3".getBytes(), value);

            client.del(tableName, hashKey, "k1".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {
            client.set(tableName, hashKey, "k3".getBytes(), "v333v".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k4".getBytes(), "v4".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k3".getBytes(), CheckType.CT_VALUE_MATCH_ANYWHERE, "333".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v333v".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k4".getBytes());
            Assert.assertArrayEquals("v4".getBytes(), value);

            client.del(tableName, hashKey, "k3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        PegasusClientFactory.closeSingletonClient();
    }

    @Test
    public void testMatchPrefix() throws PException {
        PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
        String tableName = "temp";
        byte[] hashKey = "check_and_mutate_java_test_value_match_prefix".getBytes();

        try {
            client.del(tableName, hashKey, "k1".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k1".getBytes(), "v1".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_PREFIX, "v".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertFalse(result.checkValueExist);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertNull(value);

            client.set(tableName, hashKey, "k1".getBytes(), "".getBytes());

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_PREFIX, "v".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("".getBytes(), value);

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_PREFIX, "".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v1".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_PREFIX, "".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v111v".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_PREFIX, "v".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v111v".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_PREFIX, "111".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v111v".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v111v".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_PREFIX, "v111".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v111v".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v3".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_PREFIX, "y".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_PREFIX, "v2v".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_PREFIX, "v2".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v3".getBytes(), value);

            client.del(tableName, hashKey, "k1".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {
            client.set(tableName, hashKey, "k3".getBytes(), "v333v".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k4".getBytes(), "v4".getBytes(), 0));

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k3".getBytes(), CheckType.CT_VALUE_MATCH_PREFIX, "v333".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v333v".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k4".getBytes());
            Assert.assertArrayEquals("v4".getBytes(), value);

            client.del(tableName, hashKey, "k3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        PegasusClientFactory.closeSingletonClient();
    }

    @Test
    public void testMatchPostfix() throws PException {
        PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
        String tableName = "temp";
        byte[] hashKey = "check_and_mutate_java_test_value_match_postfix".getBytes();

        try {
            client.del(tableName, hashKey, "k1".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k1".getBytes(), "v1".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_POSTFIX, "v".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertFalse(result.checkValueExist);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertNull(value);

            client.set(tableName, hashKey, "k1".getBytes(), "".getBytes());

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_POSTFIX, "v".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("".getBytes(), value);

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_POSTFIX, "".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v1".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_POSTFIX, "".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v111v".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_POSTFIX, "2".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v111v".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_POSTFIX, "111".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v111v".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v111v".getBytes(), value);

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_POSTFIX, "111v".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v111v".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v3".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_POSTFIX, "y".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_POSTFIX, "2v2".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_MATCH_POSTFIX, "v2".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v3".getBytes(), value);

            client.del(tableName, hashKey, "k1".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {
            client.set(tableName, hashKey, "k3".getBytes(), "v333v".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k4".getBytes(), "v4".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k3".getBytes(), CheckType.CT_VALUE_MATCH_POSTFIX, "333v".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v333v".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k4".getBytes());
            Assert.assertArrayEquals("v4".getBytes(), value);

            client.del(tableName, hashKey, "k3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        PegasusClientFactory.closeSingletonClient();
    }

    @Test
    public void testBytesCompare() throws PException {
        PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
        String tableName = "temp";
        byte[] hashKey = "check_and_set_java_test_value_bytes_compare".getBytes();

        try {
            client.del(tableName, hashKey, "k1".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k1".getBytes(), "v1".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_BYTES_EQUAL, "".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertFalse(result.checkValueExist);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertNull(value);

            client.set(tableName, hashKey, "k1".getBytes(), "".getBytes());

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_BYTES_EQUAL, "".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v1".getBytes(), value);

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_BYTES_EQUAL, "".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v1".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_BYTES_EQUAL, "v1".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            client.del(tableName, hashKey, "k1".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {
            client.set(tableName, hashKey, "k3".getBytes(), "v3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k4".getBytes(), "v4".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k3".getBytes(), CheckType.CT_VALUE_BYTES_EQUAL, "v3".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v3".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k4".getBytes());
            Assert.assertArrayEquals("v4".getBytes(), value);

            client.del(tableName, hashKey, "k3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {
            client.set(tableName, hashKey, "k5".getBytes(), "v1".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            // v1 < v2
            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k5".getBytes(), "v2".getBytes(), 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k5".getBytes(), CheckType.CT_VALUE_BYTES_LESS, "v2".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k5".getBytes());
            Assert.assertArrayEquals("v2".getBytes(), value);

            // v2 <= v2
            options.returnCheckValue = true;
            mutates.get(0).value = "v3".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k5".getBytes(), CheckType.CT_VALUE_BYTES_LESS_OR_EQUAL, "v2".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k5".getBytes());
            Assert.assertArrayEquals("v3".getBytes(), value);

            // v3 <= v4
            options.returnCheckValue = true;
            mutates.get(0).value = "v4".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k5".getBytes(), CheckType.CT_VALUE_BYTES_LESS_OR_EQUAL, "v4".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v3".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k5".getBytes());
            Assert.assertArrayEquals("v4".getBytes(), value);

            // v4 >= v4
            options.returnCheckValue = true;
            mutates.get(0).value = "v5".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k5".getBytes(), CheckType.CT_VALUE_BYTES_GREATER_OR_EQUAL, "v4".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v4".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k5".getBytes());
            Assert.assertArrayEquals("v5".getBytes(), value);

            // v5 >= v4
            options.returnCheckValue = true;
            mutates.get(0).value = "v6".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k5".getBytes(), CheckType.CT_VALUE_BYTES_GREATER_OR_EQUAL, "v4".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v5".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k5".getBytes());
            Assert.assertArrayEquals("v6".getBytes(), value);

            // v6 > v5
            options.returnCheckValue = true;
            mutates.get(0).value = "v7".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k5".getBytes(), CheckType.CT_VALUE_BYTES_GREATER, "v5".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v6".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k5".getBytes());
            Assert.assertArrayEquals("v7".getBytes(), value);

            client.del(tableName, hashKey, "k5".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        PegasusClientFactory.closeSingletonClient();
    }

    @Test
    public void testIntCompare() throws PException {
        PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
        String tableName = "temp";
        byte[] hashKey = "check_and_set_java_test_value_int_compare".getBytes();

        try {
            client.del(tableName, hashKey, "k1".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k1".getBytes(), "v1".getBytes(), 0));

            options.returnCheckValue = true;
            mutates.get(0).value = "2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_INT_EQUAL, "1".getBytes(),
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertFalse(result.checkValueExist);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertNull(value);

            client.set(tableName, hashKey, "k1".getBytes(), "".getBytes());

            options.returnCheckValue = true;
            try {
                client.checkAndMutate(tableName, hashKey,
                        "k1".getBytes(), CheckType.CT_VALUE_INT_EQUAL, "1".getBytes(),
                        mutates, options);
                Assert.fail();
            } catch (PException ex) {
                Assert.assertTrue(ex.getMessage(), ex.getMessage().endsWith("rocksdb error: 4"));

            } catch (Exception ex) {
                Assert.fail();
            }
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("".getBytes(), value);

            client.set(tableName, hashKey, "k1".getBytes(), "1".getBytes());

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_INT_EQUAL, "1".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("2".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "3".getBytes();
            try {
                client.checkAndMutate(tableName, hashKey,
                        "k1".getBytes(), CheckType.CT_VALUE_INT_EQUAL, "".getBytes(),
                        mutates, options);
                Assert.fail();
            } catch (PException ex) {
                Assert.assertTrue(ex.getMessage(), ex.getMessage().endsWith("rocksdb error: 4"));

            } catch (Exception ex) {
                Assert.fail();
            }
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("2".getBytes(), value);

            options.returnCheckValue = true;
            try {
                client.checkAndMutate(tableName, hashKey,
                        "k1".getBytes(), CheckType.CT_VALUE_INT_EQUAL, "v1".getBytes(),
                        mutates, options);
                Assert.fail();
            } catch (PException ex) {
                Assert.assertTrue(ex.getMessage(), ex.getMessage().endsWith("rocksdb error: 4"));

            } catch (Exception ex) {
                Assert.fail();
            }
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("2".getBytes(), value);

            options.returnCheckValue = true;

            try {
                client.checkAndMutate(tableName, hashKey,
                        "k1".getBytes(), CheckType.CT_VALUE_INT_EQUAL,
                        "88888888888888888888888888888888888888888888888".getBytes(),
                        mutates, options);
                Assert.fail();
            } catch (PException ex) {
                Assert.assertTrue(ex.getMessage(), ex.getMessage().endsWith("rocksdb error: 4"));

            } catch (Exception ex) {
                Assert.fail();
            }
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("2".getBytes(), value);

            client.set(tableName, hashKey, "k1".getBytes(), "0".getBytes());

            options.returnCheckValue = true;
            mutates.get(0).value = "-1".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_INT_EQUAL, "0".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("0".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("-1".getBytes(), value);

            options.returnCheckValue = true;
            mutates.get(0).value = "-2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_INT_EQUAL, "-1".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("-1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("-2".getBytes(), value);

            client.del(tableName, hashKey, "k1".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {
            client.set(tableName, hashKey, "k3".getBytes(), "3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k4".getBytes(), "".getBytes(), 0));

            options.returnCheckValue = true;
            mutates.get(0).value = "4".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k3".getBytes(), CheckType.CT_VALUE_INT_EQUAL, "3".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("3".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k4".getBytes());
            Assert.assertArrayEquals("4".getBytes(), value);

            client.del(tableName, hashKey, "k3".getBytes());
            client.del(tableName, hashKey, "k4".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {
            client.set(tableName, hashKey, "k5".getBytes(), "1".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k5".getBytes(), "".getBytes(), 0));

            // 1 < 2
            options.returnCheckValue = true;
            mutates.get(0).value = "2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k5".getBytes(), CheckType.CT_VALUE_INT_LESS, "2".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k5".getBytes());
            Assert.assertArrayEquals("2".getBytes(), value);

            // 2 <= 2
            options.returnCheckValue = true;
            mutates.get(0).value = "3".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k5".getBytes(), CheckType.CT_VALUE_INT_LESS_OR_EQUAL, "2".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("2".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k5".getBytes());
            Assert.assertArrayEquals("3".getBytes(), value);

            // 3 <= 4
            options.returnCheckValue = true;
            mutates.get(0).value = "4".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k5".getBytes(), CheckType.CT_VALUE_INT_LESS_OR_EQUAL, "4".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("3".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k5".getBytes());
            Assert.assertArrayEquals("4".getBytes(), value);

            // 4 >= 4
            options.returnCheckValue = true;
            mutates.get(0).value = "5".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k5".getBytes(), CheckType.CT_VALUE_INT_GREATER_OR_EQUAL, "4".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("4".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k5".getBytes());
            Assert.assertArrayEquals("5".getBytes(), value);

            // 5 >= 4
            options.returnCheckValue = true;
            mutates.get(0).value = "6".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k5".getBytes(), CheckType.CT_VALUE_INT_GREATER_OR_EQUAL, "4".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("5".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k5".getBytes());
            Assert.assertArrayEquals("6".getBytes(), value);

            // 6 > 5
            options.returnCheckValue = true;
            mutates.get(0).value = "7".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k5".getBytes(), CheckType.CT_VALUE_INT_GREATER, "5".getBytes(),
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("6".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k5".getBytes());
            Assert.assertArrayEquals("7".getBytes(), value);

            client.del(tableName, hashKey, "k5".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }

        PegasusClientFactory.closeSingletonClient();
    }

    @Test
    public void testMultiMutations() throws PException{
        PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
        String tableName = "temp";
        byte[] hashKey = "check_and_mutate_java_test_value_not_empty".getBytes();

        try {
            client.del(tableName, hashKey, "k1".getBytes());

            CheckAndMutateOptions options = new CheckAndMutateOptions();
            PegasusTableInterface.CheckAndMutateResult result;
            byte[] value;
            List<PegasusTableInterface.Mutate> mutates = new ArrayList<PegasusTableInterface.Mutate>();

            options.returnCheckValue = true;
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_PUT, "k1".getBytes(), "v1".getBytes(), 0));
            mutates.add(new PegasusTableInterface.Mutate(mutate_operation.MO_DELETE, "k1".getBytes(), null, 0));
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_NOT_EMPTY, null,
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertFalse(result.checkValueExist);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertNull(value);

            client.set(tableName, hashKey, "k1".getBytes(), "".getBytes());

            options.returnCheckValue = true;
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_NOT_EMPTY, null,
                    mutates, options);
            Assert.assertFalse(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertArrayEquals("".getBytes(), value);

            client.set(tableName, hashKey, "k1".getBytes(), "v1".getBytes());

            options.returnCheckValue = true;
            mutates.get(0).value = "v2".getBytes();
            result = client.checkAndMutate(tableName, hashKey,
                    "k1".getBytes(), CheckType.CT_VALUE_NOT_EMPTY, null,
                    mutates, options);
            Assert.assertTrue(result.succeed);
            Assert.assertTrue(result.checkValueReturned);
            Assert.assertTrue(result.checkValueExist);
            Assert.assertArrayEquals("v1".getBytes(), result.checkValue);
            value = client.get(tableName, hashKey, "k1".getBytes());
            Assert.assertNull(value);

            client.del(tableName, hashKey, "k1".getBytes());
        } catch (PException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}

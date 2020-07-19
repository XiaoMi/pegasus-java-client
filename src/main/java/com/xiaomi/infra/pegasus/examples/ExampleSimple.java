// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.examples;

import com.xiaomi.infra.pegasus.client.*;

import java.util.ArrayList;
import java.util.List;

public class ExampleSimple {

    public static void main(String[] args) throws PException {
        PegasusClientInterface client = PegasusClientFactory.createClient(ClientOptions.builder().build());

        PegasusTableInterface table = client.openTable("temp");

        byte[] value = new byte[1000];

        for (int i = 0; i < 1000; i++) {
            value[i] = 'x';
        }

        for (int t = 0; t < 10; t++) {
            List<byte[]> sortKeys = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                sortKeys.add(new byte[]{'s'});
            }

            for (int i = 0; i < 10; i++) {
                table.set(new byte[]{'s'}, sortKeys.get(i), value, 3000);
            }

            for (int i = 0; i< 10; i++) {
                table.get(new byte[]{'s'}, sortKeys.get(i), 3000);
            }
            table.multiGet(new byte[]{'s'}, sortKeys, 2000);
        }
    }
}

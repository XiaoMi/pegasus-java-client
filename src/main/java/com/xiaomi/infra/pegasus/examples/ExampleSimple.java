// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.examples;

import com.xiaomi.infra.pegasus.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExampleSimple {

    private static final Logger log = LoggerFactory.getLogger(ExampleSimple.class);

    public static void main(String[] args) throws PException {
        PegasusClientInterface client = PegasusClientFactory.createClient(ClientOptions.create());
        log.info("client-------------{}", client);
        PegasusTableInterface table = client.openTable("temp");

        byte[] value = new byte[1000];

        for (int i = 0; i < 1000; i++) {
            value[i] = 'x';
        }

        for (int t = 0; t < 10; t++) {
            List<byte[]> sortKeys = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                sortKeys.add(("sort" + i).getBytes());
            }

            for (int i = 0; i < 10; i++) {
                table.set("hash".getBytes(), sortKeys.get(i), value, 3000);
            }

            for (int i = 0; i< 10; i++) {
                table.get("hash".getBytes(), sortKeys.get(i), 3000);
            }
            table.multiGet("hash".getBytes(), sortKeys, 3000);
        }
        client.close();
    }
}

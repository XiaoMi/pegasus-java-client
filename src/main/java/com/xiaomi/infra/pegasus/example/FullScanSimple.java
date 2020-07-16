// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.example;

import com.xiaomi.infra.pegasus.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FullScanSimple {

    private static final Logger logger = LoggerFactory.getLogger(FullScanSimple.class);

    public static void main(String[] args) throws PException {
        PegasusClientInterface client = PegasusClientFactory.getSingletonClient("example/pegasus-client-config.json");

        PegasusTableInterface table = client.openTable("temp");

        char[] value = new char[1000];

        for (int i = 0; i < 1000; i++) {
            value[i] = 'x';
        }

        for (int t = 0; t < 10; t++) {
            char[][] sortKeys;
            for (int i = 0; i < 10; i++) {
                char[][] sortKey = 'sort' + i;
            }
        }
    }
}

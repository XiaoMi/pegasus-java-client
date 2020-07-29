// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.example;

import com.xiaomi.infra.pegasus.client.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;

/** Example sample */
public class ExampleSample {
  public static void main(String[] args) throws PException, UnsupportedEncodingException {
    PegasusClientInterface client = PegasusClientFactory.createClient(ClientOptions.create());
    PegasusTableInterface table = client.openTable("temp");

    byte[] value = RandomStringUtils.random(1000, true, true).getBytes("UTF-8");

    for (int t = 0; t < 10; t++) {
      List<byte[]> sortKeys = new ArrayList<>();
      for (int i = 0; i < 10; i++) {
        sortKeys.add(("sort" + i).getBytes("UTF-8"));
      }

      for (int i = 0; i < 10; i++) {
        table.set("hash".getBytes("UTF-8"), sortKeys.get(i), value, 3000);
      }

      for (int i = 0; i < 10; i++) {
        byte[] res = table.get("hash".getBytes("UTF-8"), sortKeys.get(i), 3000);
      }
      table.multiGet("hash".getBytes("UTF-8"), sortKeys, 3000);
    }
    client.close();
  }
}

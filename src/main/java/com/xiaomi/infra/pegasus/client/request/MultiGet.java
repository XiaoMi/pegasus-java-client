package com.xiaomi.infra.pegasus.client.request;

import java.util.List;

public class MultiGet {
    byte[] hashKey;
    List<byte[]> sortKeys;

    int maxFetchCount;
    int maxFetchSize;

}

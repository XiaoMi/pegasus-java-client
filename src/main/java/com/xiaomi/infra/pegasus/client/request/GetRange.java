package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.MultiGetOptions;

public class GetRange {
    byte[] hashKey;
    byte[] startSortKey;
    byte[] stopSortKey;
    int maxFetchCount;
    int maxFetchSize;

    MultiGetOptions options;

}

package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.DelRangeOptions;

public class DelRange {

    byte[] hashKey;
    byte[] startSortKey;
    byte[] stopSortKey;
    DelRangeOptions options;

}

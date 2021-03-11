package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.DelRangeOptions;

public class DeleteRange {
    private DelRangeOptions delRangeOptions;
    private byte[] startSortKey;
    private byte[] stopSortKey;
    private int timeout;
}

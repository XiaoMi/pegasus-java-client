package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.ScanOptions;

public class DeleteRange extends Scan{

    public DeleteRange(byte[] hashKey, int timeout) {
        super(hashKey, timeout);
    }


}

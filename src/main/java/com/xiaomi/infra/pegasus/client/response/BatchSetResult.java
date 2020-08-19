package com.xiaomi.infra.pegasus.client.response;

import com.xiaomi.infra.pegasus.client.PException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BatchSetResult {
    AtomicInteger successCount = new AtomicInteger(0);
    List<PException> results = new ArrayList<>();

    void add(PException pe) {
        results.add(pe);
    }

    void addSuccssCount(){
        successCount.incrementAndGet();
    }
}

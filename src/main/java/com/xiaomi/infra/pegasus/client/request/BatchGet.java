package com.xiaomi.infra.pegasus.client.request;

import java.util.ArrayList;
import java.util.List;

public class BatchGet {
    boolean forceComplete;

    public List<MultiGet> multiGetList = new ArrayList<>();
    public List<Get> getList = new ArrayList<>();

    public BatchGet(boolean forceComplete) {
        this.forceComplete = forceComplete;
    }

    public void add(MultiGet multiGet){
        multiGetList.add(multiGet);
    }

    public void add(Get get){
        getList.add(get);
    }
}

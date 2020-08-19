package com.xiaomi.infra.pegasus.client.request;

import java.util.ArrayList;
import java.util.List;

public class BatchSet {
    public List<MultiSet> multiSetList = new ArrayList<>();
    public List<Set> setList = new ArrayList<>();

    public void add(MultiSet multiSet){
        multiSetList.add(multiSet);
    }

    public void add(Set set){
        setList.add(set);
    }

}

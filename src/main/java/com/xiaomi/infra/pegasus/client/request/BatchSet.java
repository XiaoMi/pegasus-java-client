package com.xiaomi.infra.pegasus.client.request;

import java.util.ArrayList;
import java.util.List;

public class BatchSet {
  public boolean forceComplete;
  public List<Set> setList = new ArrayList<>();

  public BatchSet(boolean forceComplete) {
    this.forceComplete = forceComplete;
  }

  public BatchSet(boolean forceComplete, List<Set> setList) {
    this.forceComplete = forceComplete;
    this.setList = setList;
  }

  public void add(Set set) {
    setList.add(set);
  }
}

package com.xiaomi.infra.pegasus.client.request;

import java.util.ArrayList;
import java.util.List;

public class BatchGet {
  public boolean forceComplete;

  public List<Get> getList = new ArrayList<>();

  public BatchGet(boolean forceComplete) {
    this.forceComplete = forceComplete;
  }

  public BatchGet(boolean forceComplete, List<Get> getList) {
    this.forceComplete = forceComplete;
    this.getList = getList;
  }

  public void add(Get get) {
    getList.add(get);
  }
}

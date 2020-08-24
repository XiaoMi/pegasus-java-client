package com.xiaomi.infra.pegasus.client.request;

import java.util.ArrayList;
import java.util.List;

public class BatchDelete {

  public boolean forceComplete;

  public List<Delete> deleteList = new ArrayList<>();

  public BatchDelete(boolean forceComplete) {
    this.forceComplete = forceComplete;
  }

  public BatchDelete(boolean forceComplete, List<Delete> deleteList) {
    this.forceComplete = forceComplete;
    this.deleteList = deleteList;
  }

  public void add(Delete delete) {
    deleteList.add(delete);
  }
}

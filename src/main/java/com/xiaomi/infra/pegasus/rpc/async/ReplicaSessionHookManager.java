package com.xiaomi.infra.pegasus.rpc.async;

import java.util.ArrayList;
import java.util.List;

public class ReplicaSessionHookManager {
  private List<ReplicaSessionHook> hooks = new ArrayList<>();
  private static ReplicaSessionHookManager instance = new ReplicaSessionHookManager();

  public static ReplicaSessionHookManager instance() {
    return instance;
  }

  void addHook(ReplicaSessionHook hook) {
    hooks.add(hook);
  }

  public void onConnected(ReplicaSession session) {
    for (ReplicaSessionHook hook : hooks) {
      hook.onConnected(session);
    }
  }
}

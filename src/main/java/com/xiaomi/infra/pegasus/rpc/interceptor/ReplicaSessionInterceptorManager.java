package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.xiaomi.infra.pegasus.rpc.async.ReplicaSession;
import java.util.ArrayList;
import java.util.List;

public class ReplicaSessionInterceptorManager {
  private List<ReplicaSessionInterceptor> hooks = new ArrayList<>();
  private static ReplicaSessionInterceptorManager instance = new ReplicaSessionInterceptorManager();

  public static ReplicaSessionInterceptorManager instance() {
    return instance;
  }

  public void addHook(ReplicaSessionInterceptor hook) {
    hooks.add(hook);
  }

  public void onConnected(ReplicaSession session) {
    for (ReplicaSessionInterceptor hook : hooks) {
      hook.onConnected(session);
    }
  }
}

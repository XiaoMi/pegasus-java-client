package com.xiaomi.infra.pegasus.rpc.async;

public interface ReplicaSessionHook {
  // The behavior when a rpc session is connected.
  void onConnected(ReplicaSession session);
}

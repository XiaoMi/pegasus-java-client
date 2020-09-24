package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.xiaomi.infra.pegasus.rpc.async.ReplicaSession;

public interface ReplicaSessionInterceptor {
  // The behavior when a rpc session is connected.
  void onConnected(ReplicaSession session);

  // The behavior when rpc session is sending a message.
  boolean onSendMessage(ReplicaSession session, final ReplicaSession.RequestEntry entry);
}

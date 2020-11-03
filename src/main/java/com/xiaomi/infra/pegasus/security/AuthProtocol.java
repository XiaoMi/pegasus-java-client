package com.xiaomi.infra.pegasus.security;

import com.xiaomi.infra.pegasus.rpc.async.ReplicaSession;

public interface AuthProtocol {
  void authenticate(ReplicaSession session);
}

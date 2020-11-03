package com.xiaomi.infra.pegasus.security;

import com.xiaomi.infra.pegasus.rpc.async.ReplicaSession;

/**
 * authentiation protocol
 **/
public interface AuthProtocol {
  /**
   * begin the authentiate process
   **/
  void authenticate(ReplicaSession session);
}

package com.xiaomi.infra.pegasus.tools.interceptor;


public interface TableInterceptor {
  // The behavior before the ReplicaSession sends the RPC.
  void interceptBefore() throws Exception;
  // The behavior after the ReplicaSession get reply or failure of the RPC.
  void interceptAfter() throws Exception;

  boolean isEnable();
}

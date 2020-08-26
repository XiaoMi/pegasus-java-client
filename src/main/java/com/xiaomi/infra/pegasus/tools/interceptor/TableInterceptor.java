package com.xiaomi.infra.pegasus.tools.interceptor;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;

public interface TableInterceptor {
  // The behavior before the ReplicaSession sends the RPC.
  void interceptBefore(ClientRequestRound clientRequestRound, TableHandler tableHandler)
      throws PException;
  // The behavior after the ReplicaSession get reply or failure of the RPC.
  void interceptAfter(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler)
      throws PException;
}

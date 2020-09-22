package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.xiaomi.infra.pegasus.client.ClientOptions;
import com.xiaomi.infra.pegasus.rpc.async.ReplicaSession;
import java.util.ArrayList;
import java.util.List;

public class ReplicaSessionInterceptorManager {
  private List<ReplicaSessionInterceptor> interceptors = new ArrayList<>();
  private static ReplicaSessionInterceptorManager instance = new ReplicaSessionInterceptorManager();

  public static ReplicaSessionInterceptorManager instance() {
    return instance;
  }

  public void addSecurityInterceptor(ClientOptions options) {
    ReplicaSessionInterceptor securityInterceptor =
        new SecurityReplicaSessionInterceptor(
            options.getJaasConf(), options.getServiceName(), options.getServiceFQDN());
    interceptors.add(securityInterceptor);
  }

  public void onConnected(ReplicaSession session) {
    for (ReplicaSessionInterceptor interceptor : interceptors) {
      interceptor.onConnected(session);
    }
  }
}

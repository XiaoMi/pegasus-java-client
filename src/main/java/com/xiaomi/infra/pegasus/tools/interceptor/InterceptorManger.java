package com.xiaomi.infra.pegasus.tools.interceptor;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;
import java.util.ArrayList;
import java.util.List;

public class InterceptorManger {

  private List<TableInterceptor> interceptors = new ArrayList<>();

  public InterceptorManger add(TableInterceptor interceptor) {
    interceptors.add(interceptor);
    return this;
  }

  public void executeBefore(ClientRequestRound clientRequestRound, TableHandler tableHandler)
      throws PException {
    for (TableInterceptor interceptor : interceptors) {
      interceptor.interceptBefore(clientRequestRound, tableHandler);
    }
  }

  public void executeAfter(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler)
      throws PException {
    for (TableInterceptor interceptor : interceptors) {
      interceptor.interceptAfter(clientRequestRound, errno, tableHandler);
    }
  }
}

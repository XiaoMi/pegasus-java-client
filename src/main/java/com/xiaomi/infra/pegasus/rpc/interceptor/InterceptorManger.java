package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

public class InterceptorManger {
  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(InterceptorManger.class);

  private List<TableInterceptor> interceptors = new ArrayList<>();

  public InterceptorManger add(TableInterceptor interceptor) {
    interceptors.add(interceptor);
    return this;
  }

  public void interceptBefore(ClientRequestRound clientRequestRound, TableHandler tableHandler) {
    for (TableInterceptor interceptor : interceptors) {
      try {
        interceptor.interceptBefore(clientRequestRound, tableHandler);
      } catch (PException e) {
        logger.warn("interceptorBefore execute failed!", e);
      }
    }
  }

  public void interceptAfter(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler) {
    for (TableInterceptor interceptor : interceptors) {
      try {
        interceptor.interceptAfter(clientRequestRound, errno, tableHandler);
      } catch (PException e) {
        logger.warn("interceptorAfter execute failed!", e);
      }
    }
  }
}

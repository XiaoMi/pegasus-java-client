package com.xiaomi.infra.pegasus.rpc.interceptor;

import static com.xiaomi.infra.pegasus.base.error_code.error_types.ERR_INCOMPLETE_DATA;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.operator.client_operator;
import com.xiaomi.infra.pegasus.rpc.TableOptions;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

public class InterceptorManger {
  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(InterceptorManger.class);

  private List<TableInterceptor> interceptors = new ArrayList<>();

  public InterceptorManger(TableOptions options) {
    this.register(new BackupRequestInterceptor(), options.enableBackupRequest())
        .register(new CompressionInterceptor(), options.enableCompression());
  }

  private InterceptorManger register(TableInterceptor interceptor, boolean enable) {
    if (enable) {
      interceptors.add(interceptor);
    }
    return this;
  }

  public void before(ClientRequestRound clientRequestRound, TableHandler tableHandler) {
    for (TableInterceptor interceptor : interceptors) {
      try {
        interceptor.before(clientRequestRound, tableHandler);
      } catch (PException e) {
        client_operator operator = clientRequestRound.getOperator();
        operator.rpc_error.errno = ERR_INCOMPLETE_DATA;
        operator.error_message = "interceptor-before execute failed! error=" + e.getMessage();
        logger.error("interceptor-after execute failed!", e);
        clientRequestRound.thisRoundCompletion();
      }
    }
  }

  public void after(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler) {
    for (TableInterceptor interceptor : interceptors) {
      try {
        interceptor.after(clientRequestRound, errno, tableHandler);
      } catch (PException e) {
        client_operator operator = clientRequestRound.getOperator();
        operator.rpc_error.errno = ERR_INCOMPLETE_DATA;
        operator.error_message = "interceptor-after execute failed! error=" + e.getMessage();
        logger.error("interceptor-after execute failed!", e);
        clientRequestRound.thisRoundCompletion();
      }
    }
  }
}

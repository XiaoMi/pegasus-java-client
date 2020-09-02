package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.client.PException;
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
    this.register(new BackupRequestInterceptor(options.enableBackupRequest()))
        .register(new CompressInterceptor(options.enableCompress()));
  }

  private InterceptorManger register(TableInterceptor interceptor) {
    interceptors.add(interceptor);
    return this;
  }

  public void before(ClientRequestRound clientRequestRound, TableHandler tableHandler) {
    for (TableInterceptor interceptor : interceptors) {
      try {
        interceptor.before(clientRequestRound, tableHandler);
      } catch (PException e) {
        logger.warn("interceptor-before execute failed!", e);
      }
    }
  }

  public void after(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler) {
    for (TableInterceptor interceptor : interceptors) {
      try {
        interceptor.after(clientRequestRound, errno, tableHandler);
      } catch (PException e) {
        logger.warn("interceptor-after execute failed!", e);
      }
    }
  }
}

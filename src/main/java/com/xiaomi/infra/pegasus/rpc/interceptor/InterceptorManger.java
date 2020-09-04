package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.client.TableOptions;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;
import java.util.ArrayList;
import java.util.List;

public class InterceptorManger {

  private List<TableInterceptor> interceptors = new ArrayList<>();

  /**
   * The interceptor manager
   *
   * <p>Note: {@link AutoRetryInterceptor} must be added and executed before {@link
   * BackupRequestInterceptor}, for the {@link AutoRetryInterceptor} will modify the {@link
   * ClientRequestRound#timeoutMs} which is used by {@link BackupRequestInterceptor}
   *
   * @param options control the interceptor switch, detail see {@link TableOptions}
   */
  public InterceptorManger(TableOptions options) {
    if (options.enableAutoRetry()) {
      interceptors.add(new AutoRetryInterceptor(options.retryOptions()));
    }

    if (options.enableBackupRequest()) {
      interceptors.add(new BackupRequestInterceptor(options.backupRequestDelayMs()));
    }

    if (options.enableCompression()) {
      interceptors.add(new CompressionInterceptor());
    }
  }

  public void before(ClientRequestRound clientRequestRound, TableHandler tableHandler) {
    for (TableInterceptor interceptor : interceptors) {
      interceptor.before(clientRequestRound, tableHandler);
    }
  }

  public void after(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler) {
    for (TableInterceptor interceptor : interceptors) {
      interceptor.after(clientRequestRound, errno, tableHandler);
    }
  }
}

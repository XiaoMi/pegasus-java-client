package com.xiaomi.infra.pegasus.rpc.async;

import com.xiaomi.infra.pegasus.base.error_code;
import com.xiaomi.infra.pegasus.operator.client_operator;
import com.xiaomi.infra.pegasus.rpc.Meta;

public class MetaHandler extends Meta {
  private MetaSession session;

  public MetaHandler(MetaSession metaSession) {
    this.session = metaSession;
  }

  @Override
  public error_code.error_types operate(client_operator op, long timeoutMs) {
    this.session.executeWithTimeout(op, timeoutMs);
    return MetaSession.getMetaServiceError(op);
  }
}

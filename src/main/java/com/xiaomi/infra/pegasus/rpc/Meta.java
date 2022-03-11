package com.xiaomi.infra.pegasus.rpc;

import com.xiaomi.infra.pegasus.base.error_code;
import com.xiaomi.infra.pegasus.operator.client_operator;

public abstract class Meta {
  public abstract error_code.error_types operate(client_operator op, long timeoutMs);
}

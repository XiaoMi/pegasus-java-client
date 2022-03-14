// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
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

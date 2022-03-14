// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.rpc;

import com.xiaomi.infra.pegasus.base.error_code;
import com.xiaomi.infra.pegasus.operator.client_operator;

public abstract class Meta {
  public abstract error_code.error_types operate(client_operator op, long timeoutMs);
}

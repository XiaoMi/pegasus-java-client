/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.xiaomi.infra.pegasus.security;

import org.apache.commons.configuration2.Configuration;

/** credential info for authentiation */
public interface Credential {
  String KERBEROS_PROTOCOL_NAME = "kerberos";

  static Credential createCredential(String authProtocol, Configuration config) {
    Credential credential = null;
    if (authProtocol.equals(KERBEROS_PROTOCOL_NAME)) {
      credential = new KerberosCredential(config);
    }

    return credential;
  }

  /** get the authentiation protocol supported */
  AuthProtocol getProtocol();
}

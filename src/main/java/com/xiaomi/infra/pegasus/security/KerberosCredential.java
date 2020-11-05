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

class KerberosCredential implements Credential {
  public static final String PEGASUS_SERVICE_NAME_KEY = "kerberos_service_name";
  public static final String PEGASUS_SERVICE_FQDN_KEY = "kerberos_service_fqdn";
  public static final String PEGASUS_KEYTAB_KEY = "kerberos_keytab";
  public static final String PEGASUS_PRINCIPAL_KEY = "kerberos_principal";

  public static final String DEFAULT_SERVICE_NAME = "";
  public static final String DEFAULT_SERVICE_FQDN = "";
  public static final String DEFAULT_KEYTAB = "";
  public static final String DEFAULT_PRINCIPAL = "";

  private String serviceName;
  private String serviceFqdn;
  private String keyTab;
  private String principal;

  public KerberosCredential(Configuration config) {
    this.serviceName = config.getString(PEGASUS_SERVICE_NAME_KEY, DEFAULT_SERVICE_NAME);
    this.serviceFqdn = config.getString(PEGASUS_SERVICE_FQDN_KEY, DEFAULT_SERVICE_FQDN);
    this.keyTab = config.getString(PEGASUS_KEYTAB_KEY, DEFAULT_KEYTAB);
    this.principal = config.getString(PEGASUS_PRINCIPAL_KEY, DEFAULT_PRINCIPAL);
  }

  @Override
  public AuthProtocol getProtocol() {
    return new KerberosProtocol(serviceName, serviceFqdn, keyTab, principal);
  }
}

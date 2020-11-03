package com.xiaomi.infra.pegasus.security;

import org.apache.commons.configuration2.Configuration;

public class KerberosCredential implements Credential {
  public static final String PEGASUS_SERVICE_NAME_KEY = "service_name";
  public static final String PEGASUS_SERVICE_FQDN_KEY = "service_fqdn";
  public static final String PEGASUS_KEYTAB_KEY = "keytab";
  public static final String PEGASUS_PRINCIPAL_KEY = "principal";

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
    return new KerberosProtocol(serviceFqdn, serviceFqdn, keyTab, principal);
  }

  @Override
  public String toString() {
    return "KerberosCredential{"
        + "serviceName='"
        + serviceName
        + '\''
        + ", serviceFqdn='"
        + serviceFqdn
        + '\''
        + ", keyTab='"
        + keyTab
        + '\''
        + ", principal='"
        + principal
        + '\''
        + '}';
  }
}

package com.xiaomi.infra.pegasus.security;

import org.apache.commons.configuration2.Configuration;

/**
 * credential info for authentiation
 **/
public interface Credential {
  static Credential createCredential(String authProtocol, Configuration config) {
    Credential credential = null;
    if (authProtocol.equals("kerberos")) {
      credential = new KerberosCredential(config);
    }

    return credential;
  }

  /**
   * get the authentiation protocol supported
   **/
  public AuthProtocol getProtocol();

  public String toString();
}

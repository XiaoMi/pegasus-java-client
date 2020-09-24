package com.xiaomi.infra.pegasus.rpc.async;

import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import javax.security.auth.Subject;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;

public class SaslWrapper {
  private SaslClient saslClient;
  private Subject subject;
  private String serviceName;
  private String serviceFQDN;
  private HashMap<String, Object> props;

  public SaslWrapper(
      Subject subject, String serviceName, String serviceFQDN, HashMap<String, Object> props) {
    this.subject = subject;
    this.serviceName = serviceName;
    this.serviceFQDN = serviceFQDN;
    this.props = props;
  }

  public byte[] init(String[] mechanims) throws PrivilegedActionException {
    return Subject.doAs(
        subject,
        (PrivilegedExceptionAction<byte[]>)
            () -> {
              saslClient =
                  Sasl.createSaslClient(mechanims, null, serviceName, serviceFQDN, props, null);
              return saslClient.getMechanismName().getBytes();
            });
  }
}

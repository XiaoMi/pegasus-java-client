package com.xiaomi.infra.pegasus.rpc.async;

import com.sun.security.auth.callback.TextCallbackHandler;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.slf4j.Logger;

public class SecurityReplicaSessionHook implements ReplicaSessionHook {
  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ClusterManager.class);
  private String jaasConf;
  private String serviceName;
  private String serviceFqdn;
  private Subject subject;
  private LoginContext loginContext;

  public SecurityReplicaSessionHook(String jaasConf, String serviceName, String serviceFqdn) {
    this.serviceName = serviceName;
    this.serviceFqdn = serviceFqdn;
    this.jaasConf = jaasConf;
    System.setProperty("java.security.auth.login.config", this.jaasConf);

    try {
      loginContext = new LoginContext("client", new TextCallbackHandler());
    } catch (Exception e) {
      logger.error("cannot create LoginContext", e);
      System.exit(-1);
    }

    try {
      loginContext.login();
    } catch (LoginException le) {
      logger.error("authentication failed", le);
      System.exit(-1);
    }

    subject = loginContext.getSubject();
    logger.info("login succeed, as user {}", subject.getPrincipals().toString());
  }

  public void onConnected(ReplicaSession session) {
    Negotiation negotiation = new Negotiation(session, subject, serviceName, serviceFqdn);
    negotiation.start();
  }
}

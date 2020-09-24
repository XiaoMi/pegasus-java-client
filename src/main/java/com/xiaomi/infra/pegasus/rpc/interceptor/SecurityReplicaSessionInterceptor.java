package com.xiaomi.infra.pegasus.rpc.interceptor;

import com.sun.security.auth.callback.TextCallbackHandler;
import com.xiaomi.infra.pegasus.operator.negotiation_operator;
import com.xiaomi.infra.pegasus.rpc.async.Negotiation;
import com.xiaomi.infra.pegasus.rpc.async.ReplicaSession;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import org.slf4j.Logger;

public class SecurityReplicaSessionInterceptor implements ReplicaSessionInterceptor {
  private static final Logger logger =
      org.slf4j.LoggerFactory.getLogger(SecurityReplicaSessionInterceptor.class);

  private String serviceName;
  private String serviceFqdn;
  private Subject subject;
  private LoginContext loginContext;

  public SecurityReplicaSessionInterceptor(String serviceName, String serviceFqdn)
      throws IllegalArgumentException {
    this.serviceName = serviceName;
    this.serviceFqdn = serviceFqdn;

    try {
      loginContext = new LoginContext("client", new TextCallbackHandler());
      loginContext.login();

      subject = loginContext.getSubject();
      if (subject == null) {
        throw new LoginException("subject is null");
      }
    } catch (LoginException le) {
      throw new IllegalArgumentException("login failed", le);
    }

    logger.info("login succeed, as user {}", subject.getPrincipals().toString());
  }

  public void onConnected(ReplicaSession session) {
    Negotiation negotiation = new Negotiation(session, subject, serviceName, serviceFqdn);
    negotiation.start();
  }

  public boolean onSendMessage(ReplicaSession session, final ReplicaSession.RequestEntry entry) {
    // tryPendRequest returns false means that the negotiation is succeed now
    return isNegotiationRequest(entry) || !session.tryPendRequest(entry);
  }

  private boolean isNegotiationRequest(final ReplicaSession.RequestEntry entry) {
    return entry.op.getClass().equals(negotiation_operator.class);
  }
}

package com.xiaomi.infra.pegasus.rpc.async;

import com.xiaomi.infra.pegasus.apps.negotiation_request;
import com.xiaomi.infra.pegasus.apps.negotiation_response;
import com.xiaomi.infra.pegasus.apps.negotiation_status;
import com.xiaomi.infra.pegasus.base.blob;
import com.xiaomi.infra.pegasus.base.error_code;
import com.xiaomi.infra.pegasus.operator.negotiation_operator;
import com.xiaomi.infra.pegasus.rpc.ReplicationException;
import org.slf4j.Logger;

public class Negotiation {
  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Negotiation.class);

  private ReplicaSession session;
  private negotiation_status status;
  private static final int defaultTimeoutMs = 3000;

  public Negotiation(ReplicaSession session) {
    this.session = session;
  }

  public void start() {
    status = negotiation_status.SASL_LIST_MECHANISMS;
    send(status, new blob(new byte[0]));
  }

  public void send(negotiation_status status, blob msg) {
    negotiation_request request = new negotiation_request();
    request.status = status;
    request.msg = msg;
    session.sendNegoMsg(request, defaultTimeoutMs);
  }

  public static class RecvHandler implements Runnable {
    negotiation_operator op;
    ReplicaSession session;

    RecvHandler(negotiation_operator op, ReplicaSession session) {
      this.op = op;
      this.session = session;
    }

    @Override
    public void run() {
      try {
        if (op.rpc_error.errno != error_code.error_types.ERR_OK) {
          throw new ReplicationException(op.rpc_error.errno);
        }
        handleResponse();
      } catch (Exception e) {
        logger.error("Negotiation failed", e);
        session.markSessionDisconnect();
      }
    }

    private void handleResponse() throws Exception {
      final negotiation_response resp = op.get_response();
      if (resp == null) {
        logger.error("RecvHandler received a null response, abandon it");
        return;
      }

      switch (resp.status) {
        case SASL_LIST_MECHANISMS_RESP:
        case SASL_SELECT_MECHANISMS_RESP:
        case SASL_CHALLENGE:
        case SASL_SUCC:
          break;
        default:
          throw new Exception("Received an unexpected response, status " + resp.status);
      }
    }
  }

  public negotiation_status get_status() {
    return status;
  }
}

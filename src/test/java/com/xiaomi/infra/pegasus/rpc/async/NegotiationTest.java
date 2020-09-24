package com.xiaomi.infra.pegasus.rpc.async;

import static org.mockito.ArgumentMatchers.any;

import com.xiaomi.infra.pegasus.apps.negotiation_status;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class NegotiationTest {
  private Negotiation negotiation = new Negotiation(null, null, "", "");

  @Test
  public void testStart() {
    Negotiation mockNegotiation = Mockito.spy(negotiation);

    Mockito.doNothing().when(mockNegotiation).send(any(), any());
    mockNegotiation.start();
    Assert.assertEquals(mockNegotiation.getStatus(), negotiation_status.SASL_LIST_MECHANISMS);
  }

  @Test
  public void tetGetMatchMechanism() {
    String matchMechanism = negotiation.getMatchMechanism("GSSAPI,ABC");
    Assert.assertEquals(matchMechanism, "GSSAPI");

    matchMechanism = negotiation.getMatchMechanism("TEST,ABC");
    Assert.assertEquals(matchMechanism, "");
  }

  @Test
  public void testCheckStatus() {
    negotiation_status status = negotiation_status.SASL_LIST_MECHANISMS;
    negotiation_status expectedStatus = negotiation_status.SASL_LIST_MECHANISMS;
    try {
      negotiation.checkStatus(status, expectedStatus);
    } catch (Exception e) {
      Assert.fail();
    }

    status = negotiation_status.SASL_LIST_MECHANISMS_RESP;
    try {
      negotiation.checkStatus(status, expectedStatus);
      Assert.fail();
    } catch (Exception e) {
    }
  }
}

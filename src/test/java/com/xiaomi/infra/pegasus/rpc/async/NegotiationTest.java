package com.xiaomi.infra.pegasus.rpc.async;

import static org.mockito.ArgumentMatchers.any;

import com.xiaomi.infra.pegasus.apps.negotiation_response;
import com.xiaomi.infra.pegasus.apps.negotiation_status;
import com.xiaomi.infra.pegasus.base.blob;
import javax.security.auth.Subject;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class NegotiationTest {
  private Negotiation negotiation = new Negotiation(null, new Subject(), "", "");

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

  @Test
  public void testRecvMechanisms() {
    Negotiation mockNegotiation = Mockito.spy(negotiation);
    SaslWrapper mockSaslWrapper = Mockito.mock(SaslWrapper.class);
    mockNegotiation.saslWrapper = mockSaslWrapper;

    Mockito.doNothing().when(mockNegotiation).send(any(), any());
    try {
      Mockito.when(mockNegotiation.saslWrapper.init(any())).thenReturn(new byte[0]);
    } catch (Exception ex) {
      Assert.fail();
    }

    // normal case
    try {
      negotiation_response response =
          new negotiation_response(
              negotiation_status.SASL_LIST_MECHANISMS_RESP, new blob(new byte[0]));
      mockNegotiation.onRecvMechanisms(response);
      Assert.assertEquals(mockNegotiation.getStatus(), negotiation_status.SASL_SELECT_MECHANISMS);
    } catch (Exception ex) {
      Assert.fail();
    }

    // deal with wrong response.status
    try {
      negotiation_response response =
          new negotiation_response(negotiation_status.SASL_LIST_MECHANISMS, new blob(new byte[0]));
      mockNegotiation.onRecvMechanisms(response);
      Assert.fail();
    } catch (Exception ex) {
    }
  }

  @Test
  public void testMechanismSelected() {
    Negotiation mockNegotiation = Mockito.spy(negotiation);
    SaslWrapper mockSaslWrapper = Mockito.mock(SaslWrapper.class);
    mockNegotiation.saslWrapper = mockSaslWrapper;

    Mockito.doNothing().when(mockNegotiation).send(any(), any());
    try {
      Mockito.when(mockNegotiation.saslWrapper.getInitialResponse()).thenReturn(new blob());
    } catch (Exception ex) {
      Assert.fail();
    }

    // normal case
    try {
      negotiation_response response =
          new negotiation_response(
              negotiation_status.SASL_SELECT_MECHANISMS_RESP, new blob(new byte[0]));
      mockNegotiation.onMechanismSelected(response);
      Assert.assertEquals(mockNegotiation.getStatus(), negotiation_status.SASL_INITIATE);
    } catch (Exception ex) {
      Assert.fail();
    }

    // deal with wrong response.status
    try {
      negotiation_response response =
          new negotiation_response(negotiation_status.SASL_LIST_MECHANISMS, new blob(new byte[0]));
      mockNegotiation.onMechanismSelected(response);
      Assert.fail();
    } catch (Exception ex) {
    }
  }
}

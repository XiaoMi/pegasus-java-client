package com.xiaomi.infra.pegasus.rpc.async;

import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.client.ClientOptions;
import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusClientFactory;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import com.xiaomi.infra.pegasus.rpc.ReplicationException;
import com.xiaomi.infra.pegasus.rpc.TableOptions;
import com.xiaomi.infra.pegasus.rpc.TableOptions.RetryOptions;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class InterceptorTest {
  @Test
  public void testCompressionInterceptor() throws PException {
    PegasusTableInterface commonTable =
        PegasusClientFactory.createClient(ClientOptions.create()).openTable("temp");
    PegasusTableInterface compressTable =
        PegasusClientFactory.createClient(ClientOptions.create())
            .openTable("temp", new TableOptions().withCompression(true));

    byte[] hashKey = "hashKey".getBytes();
    byte[] sortKey = "sortKey".getBytes();
    byte[] commonValue = "commonValue".getBytes();
    byte[] compressionValue = "compressionValue".getBytes();

    // if origin value was not compressed, both commonTable and compressTable can read origin value
    commonTable.set(hashKey, sortKey, commonValue, 10000);
    Assertions.assertEquals(
        new String(commonTable.get(hashKey, sortKey, 10000)), new String(commonValue));
    Assertions.assertEquals(
        new String(compressTable.get(hashKey, sortKey, 10000)), new String(commonValue));

    // if origin value was compressed, only compressTable can read successfully
    compressTable.set(hashKey, sortKey, compressionValue, 10000);
    Assertions.assertNotEquals(
        new String(commonTable.get(hashKey, sortKey, 10000)), new String(compressionValue));
    Assertions.assertEquals(
        new String(compressTable.get(hashKey, sortKey, 10000)), new String(compressionValue));
  }

  @Test
  public void testAutoRetryInterceptor() throws ReplicationException {
    TableHandler table = createRetryTable(300, 100);
    table.forTest(new ClientRequestRound(null, null, false, System.nanoTime() + 1000000, 1000));

    ClientRequestRound clientRequestRound = new ClientRequestRound(null, null, false, System.nanoTime() + 1000000, 1000)
    Mockito.when(
            table.forTest(clientRequestRound))
        .then(
            new Answer<Object>() {
              @Override
              public Object answer(InvocationOnMock invocation) throws Throwable {
                clientRequestRound.getOperator().rpc_error.errno = error_types.ERR_TIMEOUT;
                table.onRpcReply(clientRequestRound, 1, null);
              }
            });
  }

  private TableHandler createRetryTable(long retryTime, long delayTime)
      throws ReplicationException {
    return Mockito.spy(
        new TableHandler(
            new ClusterManager(ClientOptions.create()),
            "temp",
            new TableOptions().withRetry(new RetryOptions(retryTime, delayTime))));
  }
}

package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/**
 * This class is used for sending batch request with response, default implementations including
 * {@link GetBatch}, {@link MultiGetBatch} and so on, user can implement custom class to send more
 * types of request with response in batch, such as {@link PegasusTableInterface#incr(byte[],
 * byte[], long, int)}, the more usage can see {@link com.xiaomi.infra.pegasus.example.BatchSample}
 *
 * @param <Request> generic type for request
 * @param <Response> generic type for response
 */
public abstract class BatchWithResponse<Request, Response>
    extends AbstractBatch<Request, Response> {

  private static final long serialVersionUID = -2458231362347453846L;

  public BatchWithResponse(PegasusTableInterface table, int timeout) {
    super(table, timeout);
  }

  /**
   * send and commit batched request no-atomically, but terminate immediately if any error occurs.
   *
   * @param requests generic for request
   * @param responses generic for response
   * @throws PException any error occurs will throw exception
   */
  public void commit(List<Request> requests, List<Response> responses) throws PException {
    asyncCommitRequests(requests).waitAllCompleteOrOneFail(responses, timeout);
  }

  /**
   * send and commit batched request no-atomically, try wait for all requests done until timeout
   * even if some other error occurs.
   *
   * @param requests generic for request
   * @param responses generic for response, if one request success, the response is pair(null,
   *     result) otherwise is pair(PException, null)
   * @throws PException throw exception if timeout
   */
  public void commitWaitAllComplete(
      List<Request> requests, List<Pair<PException, Response>> responses) throws PException {
    asyncCommitRequests(requests).waitAllComplete(responses, timeout);
  }
}

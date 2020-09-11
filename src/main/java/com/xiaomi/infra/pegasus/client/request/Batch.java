package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/**
 * This class is used for sending batched request without response, default implementations
 * including {@link SetBatch}, {@link MultiSetBatch} and so on, user can implement custom class to
 * send more types of request without response in batch, the more usage can see {@link
 * com.xiaomi.infra.pegasus.example.BatchSample}
 *
 * @param <Request> generic type for request
 * @param <Response> generic type for response
 */
public abstract class Batch<Request, Response> extends AbstractBatch<Request, Response> {

  private static final long serialVersionUID = 2048811397820338392L;

  public Batch(PegasusTableInterface table, int timeout) {
    super(table, timeout);
  }

  /**
   * send and commit batched request no-atomically, but terminate immediately if any error occurs.
   *
   * @param requests generic for request
   * @throws PException any error occurs will throw exception
   */
  public void commit(List<Request> requests) throws PException {
    asyncCommitRequests(requests).waitAllCompleteOrOneFail(timeout);
  }

  /**
   * send and commit batched request no-atomically, try wait for all requests done until timeout
   * even if some other error occurs.
   *
   * @param requests generic for request
   * @param exceptions if one request is failed, the exception will save into exceptions
   * @throws PException throw exception if timeout
   */
  public void commitWaitAllComplete(List<Request> requests, List<PException> exceptions)
      throws PException {
    List<Pair<PException, Response>> responses = new ArrayList<>();
    asyncCommitRequests(requests).waitAllComplete(responses, timeout);
    convertExceptions(responses, exceptions);
  }

  private void convertExceptions(
      List<Pair<PException, Response>> responses, List<PException> exceptions) {
    for (Pair<PException, Response> response : responses) {
      exceptions.add(response.getKey());
    }
  }
}

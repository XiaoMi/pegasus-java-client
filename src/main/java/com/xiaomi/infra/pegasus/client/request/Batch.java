// Copyright (c) 2020, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.FutureGroup;
import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @param <Request>
 * @param <Response>
 */
public abstract class Batch<Request, Response> implements Serializable {

  private static final long serialVersionUID = -6267381453465488529L;

  protected PegasusTableInterface table;
  protected int timeout;

  public Batch(PegasusTableInterface table, int timeout) {
    this.table = table;
    this.timeout = timeout;
  }

  /**
   * send and commit batch request no-atomically, but terminate immediately if any error occurs.
   * Generally, it is for committing operation which need not response result, such as {@link
   * BatchSet}
   *
   * @param requests generic for request
   * @throws PException any error occurs will throw exception
   */
  public void commit(List<Request> requests) throws PException {
    asyncCommitRequests(requests).waitAllCompleteOrOneFail(timeout);
  }

  /**
   * send and commit batch request no-atomically, but terminate immediately if any error occurs.
   * Generally, it is for committing operation which need response result, such as {@link BatchSet}
   *
   * @param requests generic for request
   * @param responses generic for response
   * @throws PException any error occurs will throw exception
   */
  public void commit(List<Request> requests, List<Response> responses) throws PException {
    asyncCommitRequests(requests).waitAllCompleteOrOneFail(responses, timeout);
  }

  /**
   * send and commit batch request no-atomically, try wait for all requests done until timeout even
   * if some other error occurs
   *
   * @param requests generic for request
   * @param responses generic for response
   * @throws PException throw exception if timeout
   */
  public void commitWaitAllComplete(
      List<Request> requests, List<Pair<PException, Response>> responses) throws PException {
    asyncCommitRequests(requests).waitAllComplete(responses, timeout);
  }

  private FutureGroup<Response> asyncCommitRequests(List<Request> requests) {
    assert !requests.isEmpty() : "requests mustn't be empty";
    FutureGroup<Response> futureGroup = new FutureGroup<>(requests.size());
    for (Request request : requests) {
      futureGroup.add(asyncCommit(request));
    }
    return futureGroup;
  }

  protected abstract Future<Response> asyncCommit(Request request);
}

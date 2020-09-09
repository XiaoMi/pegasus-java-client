// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package com.xiaomi.infra.pegasus.client.request;

import com.xiaomi.infra.pegasus.client.FutureGroup;
import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import io.netty.util.concurrent.Future;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/**
 * This class is used for sending batch request, default implements contain {@link SetBatch}, {@link
 * SetBatch} and so on, user can implement custom class to send more type(such as {@link
 * PegasusTableInterface#incr(byte[], byte[], long, int)}) batch request, the more usage can see
 * {@link com.xiaomi.infra.pegasus.example.BatchSample}
 *
 * @param <Request> generic for request
 * @param <Response> generic for response
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
   * SetBatch}
   *
   * @param requests generic for request
   * @throws PException any error occurs will throw exception
   */
  public void commit(List<Request> requests) throws PException {
    asyncCommitRequests(requests).waitAllCompleteOrOneFail(timeout);
  }

  /**
   * send and commit batch request no-atomically, but terminate immediately if any error occurs.
   * Generally, it is for committing operation which need response result, such as {@link GetBatch}
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

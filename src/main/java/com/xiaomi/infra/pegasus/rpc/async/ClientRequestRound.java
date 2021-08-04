/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.xiaomi.infra.pegasus.rpc.async;

import com.xiaomi.infra.pegasus.metrics.MetricsManager;
import com.xiaomi.infra.pegasus.operator.client_operator;
import com.xiaomi.infra.pegasus.rpc.Table;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;

/** Created by weijiesun on 16-11-25. */
public final class ClientRequestRound {
  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TableHandler.class);

  client_operator operator;
  Table.ClientOPCallback callback;
  long timeoutMs;

  boolean enableCounter;
  long createNanoTime;
  long expireNanoTime;
  boolean isCompleted;
  int tryId;
  ScheduledFuture<?> backupRequestTask;

  /**
   * Constructor.
   *
   * @param op
   * @param enableCounter whether enable counter.
   */
  public ClientRequestRound(
      client_operator op,
      Table.ClientOPCallback cb,
      boolean enableCounter,
      long timeoutInMilliseconds) {
    this.operator = op;
    this.callback = cb;
    this.timeoutMs = timeoutInMilliseconds;

    this.enableCounter = enableCounter;
    this.createNanoTime = System.nanoTime();
    this.expireNanoTime = createNanoTime + TimeUnit.MILLISECONDS.toNanos(timeoutInMilliseconds);
    this.isCompleted = false;
    this.tryId = 1;
    this.backupRequestTask = null;
  }

  public ClientRequestRound(
      client_operator op,
      Table.ClientOPCallback cb,
      boolean enableCounter,
      long expireNanoTime,
      long timeoutInMilliseconds) {
    this(op, cb, enableCounter, timeoutInMilliseconds);
    this.expireNanoTime = expireNanoTime;
  }

  public long timeoutMs() {
    return timeoutMs;
  }

  public ScheduledFuture<?> backupRequestTask() {
    return backupRequestTask;
  }

  public void backupRequestTask(ScheduledFuture<?> task) {
    backupRequestTask = task;
  }

  public com.xiaomi.infra.pegasus.operator.client_operator getOperator() {
    return operator;
  }

  public void setOperator(client_operator op) {
    operator = op;
  }

  public void thisRoundCompletion() {
    try {
      callback.onCompletion(operator);
    } catch (Throwable ex) {
      // The exception is generated by the user's callback logic, so we don't do much things on it
      logger.debug("{} got exception", operator.toString(), ex);
    }

    if (enableCounter) {
      MetricsManager.updateCount(operator.getQPSCounter(), 1L);
      MetricsManager.setHistogramValue(
          operator.getLatencyCounter(), System.nanoTime() - createNanoTime);
    }
  }
}

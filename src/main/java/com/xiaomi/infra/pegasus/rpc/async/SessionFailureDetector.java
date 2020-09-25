/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiaomi.infra.pegasus.rpc.async;

import java.util.concurrent.atomic.AtomicLong;

/**
 * SessionFailureDetector detects whether the session is half-closed by the remote host, in which
 * case we need to actively close the session and reconnect.
 */
public class SessionFailureDetector {

  // Timestamp of the first timed out rpc.
  private AtomicLong firstRecentTimedOutMs;

  // Session is marked failure if all the RPCs across
  // `FAILURE_DETECT_WINDOW_MS` are timed out.
  public static final long FAILURE_DETECT_WINDOW_MS = 10 * 1000; // 10s

  public SessionFailureDetector() {
    this.firstRecentTimedOutMs = new AtomicLong(0);
  }

  /** @return true if session is confirmed to be failed. */
  public boolean markTimeout() {
    // The error must be ERR_TIMEOUT or ERR_SESSION_RESET
    long firstTs = firstRecentTimedOutMs.get();
    if (firstTs == 0) {
      // it is the first timeout in the window.
      firstRecentTimedOutMs.set(System.currentTimeMillis());
    } else if (System.currentTimeMillis() - firstTs >= FAILURE_DETECT_WINDOW_MS) {
      // ensure that session will be closed only once.
      return firstRecentTimedOutMs.compareAndSet(firstTs, 0);
    }
    return false;
  }

  /** Mark this session to be healthy. */
  public void markOK() {
    firstRecentTimedOutMs.set(0);
  }
}

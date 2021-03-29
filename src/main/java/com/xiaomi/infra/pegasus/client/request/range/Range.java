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
package com.xiaomi.infra.pegasus.client.request.range;

import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.client.PegasusTable;
import com.xiaomi.infra.pegasus.client.PegasusTableInterface;
import com.xiaomi.infra.pegasus.client.ScanOptions;

/**
 * range operation abstract class based {@linkplain PegasusTableInterface#getScanner(byte[], byte[],
 * byte[], ScanOptions)} it is implemented by {@linkplain DeleteRange} and {@linkplain GetRange}
 *
 * @param <Response> generic type for response
 */
public abstract class Range<Response> {
  public PegasusTableInterface table;
  protected byte[] hashKey;
  protected int timeout;

  protected ScanOptions scanOptions = new ScanOptions();
  protected byte[] startSortKey;
  protected byte[] stopSortKey;

  /**
   * @param table table opened
   * @param hashKey hashKey used to decide which partition to put this k-v
   * @param timeout if exceed the timeout will throw timeout exception, if <=0, it is equal with
   *     "timeout" of config
   */
  public Range(PegasusTableInterface table, byte[] hashKey, int timeout) {
    this.table = table;
    this.hashKey = hashKey;
    this.timeout = timeout <= 0 ? ((PegasusTable) table).getDefaultTimeout() : timeout;
  }

  /**
   * @param maxRangeCount the max count of range operation result
   * @return <Response> generic type for response
   * @throws PException
   */
  public abstract Response commitAndWait(int maxRangeCount) throws PException;

  /**
   * set scan options
   *
   * @param scanOptions see {@linkplain ScanOptions}
   * @return this
   */
  public Range<Response> withOptions(ScanOptions scanOptions) {
    this.scanOptions = scanOptions;
    return this;
  }

  /**
   * set startSortKey
   *
   * @param startSortKey start sort key scan from if null or length == 0, means start from begin
   * @param stopSortKey stop sort key scan to if null or length == 0, means stop to end
   * @return this
   */
  public Range<Response> withSortKeyRange(byte[] startSortKey, byte[] stopSortKey) {
    this.startSortKey = startSortKey;
    this.stopSortKey = stopSortKey;
    return this;
  }
}

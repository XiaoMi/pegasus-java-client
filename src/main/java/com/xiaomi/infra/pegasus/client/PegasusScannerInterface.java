// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

import java.util.List;
import java.util.concurrent.Future;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author shenyuannan
 *     <p>This class provides interfaces to scan data of a specified table.
 */
public interface PegasusScannerInterface {
  /**
   * Get the next item.
   *
   * @return item like {@literal <<hashKey, sortKey>, value>}; null returned if scan completed.
   * @throws PException
   */
  public Pair<Pair<byte[], byte[]>, byte[]> next() throws PException;

  /**
   * Get the next item asynchronously.
   *
   * @return A future for current op.
   *     <p>Future return: On success: if scan haven't reach the end then return the kv-pair, else
   *     return null. On failure: a throwable, which is an instance of PException.
   */
  public Future<Pair<Pair<byte[], byte[]>, byte[]>> asyncNext();

  /**
   * Get the next batch items synchronously and non-atomically
   *
   * @param results results cannot be null, and it will be clear before start next batch
   * @throws PException it will be throw PException when any single item throw exception, however,
   *     you can still use the results that has been stored successful item. And next batch will be
   *     start with the failed position in this case
   */
  public void nextBatch(List<Pair<Pair<byte[], byte[]>, byte[]>> results) throws PException;

  /** Close the scanner. Should be called when scan completed. */
  public void close();
}

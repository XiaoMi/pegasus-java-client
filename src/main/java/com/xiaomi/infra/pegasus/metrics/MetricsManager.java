// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.metrics;

import com.xiaomi.infra.pegasus.tools.Tools;
import org.slf4j.Logger;

/** Created by weijiesun on 18-3-8. */
public final class MetricsManager {

  public static void updateCount(String counterName, long count) {
    metrics.setMeter(counterName, count);
  }

  public static void setHistogramValue(String counterName, long value) {
    metrics.setHistorgram(counterName, value);
  }

  public static void initFromHost(
      String host, String tag, int reportIntervalSec, String perfCounterType) {
    synchronized (logger) {
      if (started) {
        logger.warn(
            "perf counter system({}) has started with host({}), tag({}), interval(if set falcon as system)({}), "
                + "skip this init with host({}), tag({}), interval(){}",
            MetricsManager.perfCounterType,
            MetricsManager.host,
            MetricsManager.tag,
            MetricsManager.reportIntervalSecs,
            host,
            tag,
            reportIntervalSec);
        return;
      }

      logger.info(
          "init metrics with host({}), tag({}), interval({})", host, tag, reportIntervalSec);

      MetricsManager.perfCounterType = perfCounterType;
      MetricsManager.host = host;
      MetricsManager.tag = tag;
      MetricsManager.reportIntervalSecs = reportIntervalSec;
      metrics = new MetricsPool(host, tag, reportIntervalSec, perfCounterType);
      metrics.start();
      started = true;
    }
  }

  public static void detectHostAndInit(String tag, int reportIntervalSec, String perfCounterType) {
    initFromHost(
        Tools.getLocalHostAddress().getHostName(), tag, reportIntervalSec, perfCounterType);
  }

  public static void finish() {
    synchronized (logger) {
      if (started) {
        metrics.stop();
        started = false;
      }
    }
  }

  private static boolean started = false;
  private static String host;
  private static String tag;
  private static int reportIntervalSecs;
  private static String perfCounterType;
  private static MetricsPool metrics;

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MetricsManager.class);
}

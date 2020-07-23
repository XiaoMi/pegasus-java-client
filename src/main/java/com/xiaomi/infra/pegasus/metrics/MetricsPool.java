// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.metrics;

import com.codahale.metrics.MetricRegistry;

/** Created by weijiesun on 18-3-9. */
public final class MetricsPool {

  private final MetricRegistry registry = new MetricRegistry();
  public static PegasusMonitor pegasusMonitor;

  public MetricsPool(String host, String tags, int reportStepSec, String metricType) {
    if (metricType.equals("falcon")) {
      pegasusMonitor =
          new FalconReporter(
              reportStepSec, new FalconCollector(host, tags, reportStepSec, registry));
    } else if (metricType.equals("prometheus")) {
      pegasusMonitor = new PrometheusCollector(tags, registry);
    }
  }

  public void start() {
    pegasusMonitor.start();
  }

  public void stop() {
    pegasusMonitor.stop();
  }

  public void setMeter(String counterName, long count) {
    registry.meter(counterName).mark(count);
  }

  public void setHistorgram(String counterName, long value) {
    registry.histogram(counterName).update(value);
  }

  public static String getTableTag(String counterName, String defaultTags) {
    if (defaultTags.contains("table=")) {
      return defaultTags;
    }
    String[] result = counterName.split("@");
    if (result.length >= 2) {
      return defaultTags.equals("")
          ? ("table=" + result[1])
          : (defaultTags + ",table=" + result[1]);
    }
    return defaultTags;
  }

  public static String getMetricName(String name, String suffix) {
    String[] result = name.split("@");
    if (result.length >= 2) {
      return result[0] + suffix;
    }
    return name + suffix;
  }
}

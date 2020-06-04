// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.metrics;

import com.codahale.metrics.MetricRegistry;

/** Created by weijiesun on 18-3-9. */
public final class MetricsPool {

  public final String metricType;

  public MetricsPool(String host, String tags, int reportStepSec, String type) {
    metricType = type;
    collector = new Falcon(host, tags, reportStepSec);
  }

  public MetricsPool(String tags, String type) {
    metricType = type;
    collector = new Prometheus(tags);
  }

  public void setMeter(String counterName, long count) {
    registry.meter(counterName).mark(count);
  }

  public void setHistorgram(String counterName, long value) {
    registry.histogram(counterName).update(value);
  }

  public String metricToCollector() {
    return collector.addMetric(registry);
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

  private PegasusCollector collector;
  private final MetricRegistry registry = new MetricRegistry();
}

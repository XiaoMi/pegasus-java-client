// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.metrics;

import com.codahale.metrics.MetricRegistry;

/** Created by weijiesun on 18-3-9. */
public final class MetricsPool {

  public final String metricType;

  private final MetricRegistry registry = new MetricRegistry();
  public static MetricsReporter reporter;
  public static PegasusCollector collector;

  public MetricsPool(String host, String tags, int reportStepSec, String type) {
    metricType = type;

    if (metricType.equals("falcon")) {
      collector = new Falcon(host, tags, reportStepSec, registry);
      reporter = new MetricsReporter(reportStepSec, collector);
      reporter.start();
    } else if (type.equals("prometheus")) {
      collector = new Prometheus(tags, reportStepSec, registry);
      ((Prometheus) collector).start();
    }
  }

  public void stop() {
    if (metricType.equals("falcon")) {
      reporter.stop();
    } else if (metricType.equals("prometheus")) {
      ((Prometheus) collector).stop();
    }
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
}

package com.xiaomi.infra.pegasus.metrics;

import com.codahale.metrics.MetricRegistry;

public interface PegasusCollector {

  public String addMetric(MetricRegistry registry);
}

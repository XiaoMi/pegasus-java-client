// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.metrics;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.xiaomi.infra.pegasus.tools.Tools;
import java.util.Map;
import java.util.SortedMap;
import org.json.JSONException;
import org.json.JSONObject;

/** Created by weijiesun on 18-3-9. */
public final class MetricsPool {

  private final String defaultTag;

  public MetricsPool(String host, String tags, int reportStepSec) {
    theMetric = new FalconMetric();
    theMetric.endpoint = host;
    theMetric.step = reportStepSec;
    theMetric.tags = tags;
    defaultTag = tags;
  }

  public void setMeter(String counterName, long count) {
    registry.meter(counterName).mark(count);
  }

  public void setHistorgram(String counterName, long value) {
    registry.histogram(counterName).update(value);
  }

  public void genJsonsFromMeter(String name, Meter meter, StringBuilder output)
      throws JSONException {
    theMetric.counterType = "GAUGE";

    theMetric.metric = name + ".cps-1sec";
    theMetric.tags = getTableTag(name);
    theMetric.value = meter.getMeanRate();
    oneMetricToJson(theMetric, output);
    output.append(',');
  }

  public void genJsonsFromHistogram(String name, Histogram hist, StringBuilder output)
      throws JSONException {
    theMetric.counterType = "GAUGE";
    Snapshot s = hist.getSnapshot();

    theMetric.metric = name + ".p99";
    theMetric.tags = getTableTag(name);
    theMetric.value = s.get99thPercentile();
    oneMetricToJson(theMetric, output);
    output.append(',');

    theMetric.metric = name + ".p999";
    theMetric.tags = getTableTag(name);
    theMetric.value = s.get999thPercentile();
    oneMetricToJson(theMetric, output);
    output.append(',');
  }

  public static void oneMetricToJson(FalconMetric metric, StringBuilder output)
      throws JSONException {
    JSONObject obj = new JSONObject();
    obj.put("endpoint", metric.endpoint);
    obj.put("metric", metric.metric);
    obj.put("timestamp", metric.timestamp);
    obj.put("step", metric.step);
    obj.put("value", metric.value);
    obj.put("counterType", metric.counterType);
    obj.put("tags", metric.tags);
    output.append(obj.toString());
  }

  public String metricsToJson() throws JSONException {
    theMetric.timestamp = Tools.unixEpochMills() / 1000;

    StringBuilder builder = new StringBuilder();
    builder.append('[');
    SortedMap<String, Meter> meters = registry.getMeters();
    for (Map.Entry<String, Meter> entry : meters.entrySet()) {
      genJsonsFromMeter(entry.getKey(), entry.getValue(), builder);
    }

    for (Map.Entry<String, Histogram> entry : registry.getHistograms().entrySet()) {
      genJsonsFromHistogram(entry.getKey(), entry.getValue(), builder);
    }
    builder.deleteCharAt(builder.length() - 1);
    builder.append("]");

    return builder.toString();
  }

  private String getTableTag(String counterName) {
    if (defaultTag.contains("table=")) {
      return defaultTag;
    }
    String[] result = counterName.split("@");
    if (result.length >= 2) {
      return defaultTag.equals("") ? ("table=" + result[1]) : (defaultTag + ",table=" + result[1]);
    }
    return defaultTag;
  }

  static final class FalconMetric {
    public String endpoint; // metric host
    public String metric; // metric name
    public long timestamp; // report time in unix seconds
    public int step; // report interval in seconds;
    public double value; // metric value
    public String counterType; // GAUGE or COUNTER
    public String tags; // metrics description
  }

  private FalconMetric theMetric;
  private final MetricRegistry registry = new MetricRegistry();
}

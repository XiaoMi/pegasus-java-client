package com.xiaomi.infra.pegasus.metrics;

import static com.xiaomi.infra.pegasus.metrics.MetricsPool.getTableTag;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.xiaomi.infra.pegasus.tools.Tools;
import java.util.Map;
import java.util.SortedMap;
import org.json.JSONException;
import org.json.JSONObject;

public class Falcon implements PegasusCollector {

  private FalconMetric falconMetric = new FalconMetric();
  public final String defaultTags;

  public Falcon(String host, String tags, int reportStepSec) {
    defaultTags = tags;
    falconMetric.endpoint = host;
    falconMetric.step = reportStepSec;
  }

  public String addMetric(MetricRegistry registry) {
    falconMetric.timestamp = Tools.unixEpochMills() / 1000;

    StringBuilder builder = new StringBuilder();
    builder.append('[');
    SortedMap<String, Meter> meters = registry.getMeters();
    for (Map.Entry<String, Meter> entry : meters.entrySet()) {
      genJsonsFromMeter(entry.getKey(), entry.getValue(), builder);
      builder.append(',');
    }

    for (Map.Entry<String, Histogram> entry : registry.getHistograms().entrySet()) {
      genJsonsFromHistogram(entry.getKey(), entry.getValue(), builder);
      builder.append(',');
    }

    if (builder.charAt(builder.length() - 1) == ',') {
      builder.deleteCharAt(builder.length() - 1);
    }

    builder.append("]");

    return builder.toString();
  }

  public void genJsonsFromMeter(String name, Meter meter, StringBuilder output)
      throws JSONException {
    falconMetric.counterType = "GAUGE";

    falconMetric.metric = name + ".cps-1sec";
    falconMetric.tags = getTableTag(name, defaultTags);
    falconMetric.value = meter.getMeanRate();
    oneMetricToJson(falconMetric, output);
  }

  public void genJsonsFromHistogram(String name, Histogram hist, StringBuilder output)
      throws JSONException {
    falconMetric.counterType = "GAUGE";
    Snapshot s = hist.getSnapshot();

    falconMetric.metric = name + ".p99";
    falconMetric.tags = getTableTag(name, defaultTags);
    falconMetric.value = s.get99thPercentile();
    oneMetricToJson(falconMetric, output);
    output.append(',');

    falconMetric.metric = name + ".p999";
    falconMetric.tags = getTableTag(name, defaultTags);
    falconMetric.value = s.get999thPercentile();
    oneMetricToJson(falconMetric, output);
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

  static final class FalconMetric {
    public String endpoint; // metric host
    public String metric; // metric name
    public long timestamp; // report time in unix seconds
    public int step; // report interval in seconds;
    public double value; // metric value
    public String counterType; // GAUGE or COUNTER
    public String tags; // metrics description
  }
}

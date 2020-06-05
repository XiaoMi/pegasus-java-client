package com.xiaomi.infra.pegasus.metrics;

import static com.xiaomi.infra.pegasus.metrics.MetricsPool.getTableTag;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.google.common.collect.ImmutableList;
import io.prometheus.client.Collector;
import io.prometheus.client.GaugeMetricFamily;
import io.prometheus.client.exporter.HTTPServer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

public class PrometheusCollector extends Collector implements PegasusMonitor {
  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PrometheusCollector.class);

  private static final int PORT = 9091;

  private final String defaultTags;
  private final MetricRegistry registry;
  private Map<String, Map<String, String>> tableLabels = new HashMap<>();

  public PrometheusCollector(String defaultTags, MetricRegistry registry) {
    this.defaultTags = defaultTags;
    this.registry = registry;
  }

  public List<MetricFamilySamples> collect() {
    final ImmutableList.Builder<MetricFamilySamples> metricFamilySamplesBuilder =
        ImmutableList.builder();
    updateMetric(metricFamilySamplesBuilder);
    return metricFamilySamplesBuilder.build();
  }

  public void updateMetric(final ImmutableList.Builder<MetricFamilySamples> builder) {
    for (Map.Entry<String, Meter> entry : registry.getMeters().entrySet()) {
      updateQPSMetric(entry.getKey(), entry, builder);
    }

    for (Map.Entry<String, Histogram> entry : registry.getHistograms().entrySet()) {
      updateLatencyMetric(entry.getKey(), entry, builder);
    }
  }

  private void updateQPSMetric(
      String name,
      Map.Entry<String, Meter> meter,
      final ImmutableList.Builder<MetricFamilySamples> builder) {
    Map<String, String> labels = getLabel(getTableTag(name, defaultTags));
    GaugeMetricFamily labeledGauge =
        new GaugeMetricFamily(
            formatQPSMetricName(name), "pegasus operation qps", new ArrayList<>(labels.keySet()));
    labeledGauge.addMetric(new ArrayList<>(labels.values()), meter.getValue().getMeanRate());
    builder.add(labeledGauge);
  }

  private void updateLatencyMetric(
      String name,
      Map.Entry<String, Histogram> meter,
      final ImmutableList.Builder<MetricFamilySamples> builder) {
    Map<String, String> labels = getLabel(getTableTag(name, defaultTags));
    Snapshot snapshot = meter.getValue().getSnapshot();
    updateLatencyMetric(
        formatLatencyMetricName(name, "p99"), snapshot.get99thPercentile(), labels, builder);
    updateLatencyMetric(
        formatLatencyMetricName(name, "p999"), snapshot.get999thPercentile(), labels, builder);
  }

  private void updateLatencyMetric(
      String key,
      double value,
      Map<String, String> labels,
      final ImmutableList.Builder<MetricFamilySamples> builder) {
    GaugeMetricFamily labeledGauge =
        new GaugeMetricFamily(key, "pegasus operation latency", new ArrayList<>(labels.keySet()));
    labeledGauge.addMetric(new ArrayList<>(labels.values()), value);
    builder.add(labeledGauge);
  }

  private Map<String, String> getLabel(String labels) {
    if (tableLabels.containsKey(labels)) {
      return tableLabels.get(labels);
    }

    HashMap<String, String> labelMap = new HashMap<>();
    String[] labelsString = labels.split(",");
    for (String label : labelsString) {
      String[] labelPair = label.split("=");
      assert (labelPair.length == 2);
      labelMap.put(labelPair[0], labelPair[1]);
    }
    tableLabels.put(labels, labelMap);
    return labelMap;
  }

  private String formatLatencyMetricName(String name, String percentage) {
    String[] metricName = name.split(":");
    assert (metricName.length == 2);
    return metricName[0] + "_" + percentage;
  }

  private String formatQPSMetricName(String name) {
    String[] metricName = name.split(":");
    assert (metricName.length == 2);
    return metricName[0];
  }

  @Override
  public void start() {
    try {
      register();
      new HTTPServer(PORT);
      logger.debug("start the prometheus collector server, port = {}", PORT);
    } catch (IOException e) {
      logger.debug("start the prometheus collector server failed, error = {}", e.getMessage());
    }
  }

  @Override
  public void stop() {
    logger.debug("stop the prometheus collector server, port = {}", PORT);
  }
}

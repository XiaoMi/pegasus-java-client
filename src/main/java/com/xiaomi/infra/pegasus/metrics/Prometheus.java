package com.xiaomi.infra.pegasus.metrics;

import static com.xiaomi.infra.pegasus.metrics.MetricsPool.getTableTag;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import io.prometheus.client.Collector;
import io.prometheus.client.GaugeMetricFamily;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Prometheus extends Collector implements PegasusCollector {

  private final ScheduledExecutorService collectorTask;
  private final String defaultTags;
  private final MetricRegistry registry;
  private int reportStepSec;

  private Map<String, MetricFamilySamples> metrics = new ConcurrentHashMap<>();
  private Map<String, Map<String, String>> tableLabels = new HashMap<>();

  public Prometheus(String defaultTags, int reportStepSec, MetricRegistry registry) {
    this.defaultTags = defaultTags;
    this.reportStepSec = reportStepSec;
    this.registry = registry;
    this.collectorTask = Executors.newScheduledThreadPool(1);
  }

  public List<MetricFamilySamples> collect() {
    return new ArrayList<>(metrics.values());
  }

  public String updateMetric() {
    for (Map.Entry<String, Meter> entry : registry.getMeters().entrySet()) {
      String QPSName = format(entry.getKey());
      updateQPSMetric(entry, QPSName);
    }

    for (Map.Entry<String, Histogram> entry : registry.getHistograms().entrySet()) {
      String latencyName = format(entry.getKey());
      updateLatencyMetric(entry, latencyName + "-99th");
      updateLatencyMetric(entry, latencyName + "-999th");
    }

    return "OK";
  }

  private void updateQPSMetric(Map.Entry<String, Meter> meter, String name) {
    Map<String, String> labels = getLabel(getTableTag(name, defaultTags));
    if (!metrics.containsKey(name)) {
      // assert labels != null;
      GaugeMetricFamily labeledGauge =
          new GaugeMetricFamily(name, "help", new ArrayList<>(labels.keySet()));
      labeledGauge.addMetric(new ArrayList<>(labels.values()), meter.getValue().getMeanRate());
      metrics.put(name, labeledGauge);
    }
    ((GaugeMetricFamily) metrics.get(name))
        .addMetric(new ArrayList<>(labels.values()), meter.getValue().getMeanRate());
  }

  private void updateLatencyMetric(Map.Entry<String, Histogram> meter, String name) {
    Map<String, String> labels = getLabel(getTableTag(name, defaultTags));
    ArrayList<String> labelList = new ArrayList<>(labels.values());
    Snapshot snapshot = meter.getValue().getSnapshot();
    double value =
        name.contains("99th") ? snapshot.get99thPercentile() : snapshot.get999thPercentile();
    if (!metrics.containsKey(name)) {
      // assert labels != null;
      GaugeMetricFamily labeledGauge =
          new GaugeMetricFamily(name, "help", new ArrayList<>(labels.keySet()));
      labeledGauge.addMetric(new ArrayList<>(labels.values()), value);
      metrics.put(name, labeledGauge);
    }

    ((GaugeMetricFamily) metrics.get(name)).addMetric(labelList, value);
  }

  private Map<String, String> getLabel(String labels) {
    if (tableLabels.containsKey(labels)) {
      return tableLabels.get(labels);
    }

    // todo nullptr
    HashMap<String, String> labelMap = new HashMap<>();
    String[] labelsString = labels.split(",");
    for (String label : labelsString) {
      String[] labelPair = label.split("=");
      labelMap.put(labelPair[0], labelPair[1]);
    }
    tableLabels.put(labels, labelMap);
    return labelMap;
  }

  private String format(String name) {
    return name.replaceAll("\\.", "_").replaceAll("@", ":");
  }

  public void start() {
    collectorTask.scheduleAtFixedRate(() -> updateMetric(), 0, 10, TimeUnit.SECONDS);
  }

  public void stop() {
    collectorTask.shutdown();
  }
}

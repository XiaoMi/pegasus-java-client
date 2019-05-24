// Copyright (c) 2019, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

import java.time.Duration;

/**
 * @author jiashuo1
 *     <p>This class provides method to create an instance of {@link ClientOptions}.you can use
 *     <code>
 *         ClientOptions.create();
 *     </code>
 *     <p>to create a new instance of {@link ClientOptions} with default settings, or use such as
 *     <code>
 *         ClientOptions.builder().metaServers("127.0.0.1:34601,127.0.0.1:34602,127.0.0.1:34603")
 *  *     .operationTimeout(Duration.ofMillis(1000)).asyncWorkers(4).enablePerfCounter(false)
 *  *     .falconPerfCounterTags("").falconPushInterval(Duration.ofSeconds(10)).build();
 *     </code>
 *     <p>to create an instance {@link ClientOptions} with custom settings.
 */
public class ClientOptions {

  /**
   * The list of meta server addresses, separated by commas.
   *
   * <p>Required field.
   */
  public static final String DEFAULT_META_SERVERS =
      "127.0.0.1:34601,127.0.0.1:34602,127.0.0.1:34603";

  /**
   * The timeout for failing to finish an operation.
   *
   * <p>Default: 1000ms
   */
  public static final Duration DEFAULT_OPERATION_TIMEOUT = Duration.ofMillis(1000);

  /**
   * The number of background worker threads. Internally it is the number of Netty NIO threads for
   * handling RPC events between client and Replica Servers.
   *
   * <p>Default: 4
   */
  public static final int DEFAULT_ASYNC_WORKERS = 4;

  /**
   * Whether to enable performance statistics. If true, the client will periodically report metrics
   * to local falcon agent (currently we only support falcon as monitoring system).
   *
   * <p>Default: false
   */
  public static final boolean DEFAULT_ENABLE_PERF_COUNTER = false;

  /**
   * Additional tags for falcon metrics. For example:
   * "cluster=c3srv-ad,job=recommend-service-history"
   *
   * <p>Default: empty string.
   */
  public static final String DEFAULT_FALCON_PERF_COUNTER_TAGS = "";

  /**
   * The interval to report metrics to local falcon agent.
   *
   * <p>Default: 10s
   */
  public static final Duration DEFAULT_FALCON_PUSH_INTERVAL = Duration.ofSeconds(10);

  private final String metaServers;
  private final Duration operationTimeout;
  private final int asyncWorkers;
  private final boolean enablePerfCounter;
  private final String falconPerfCounterTags;
  private final Duration falconPushInterval;

  protected ClientOptions(Builder builder) {
    this.metaServers = builder.metaServers;
    this.operationTimeout = builder.operationTimeout;
    this.asyncWorkers = builder.asyncWorkers;
    this.enablePerfCounter = builder.enablePerfCounter;
    this.falconPerfCounterTags = builder.falconPerfCounterTags;
    this.falconPushInterval = builder.falconPushInterval;
  }

  protected ClientOptions(ClientOptions original) {
    this.metaServers = original.getMetaServers();
    this.operationTimeout = original.getOperationTimeout();
    this.asyncWorkers = original.getAsyncWorkers();
    this.enablePerfCounter = original.isEnablePerfCounter();
    this.falconPerfCounterTags = original.getFalconPerfCounterTags();
    this.falconPushInterval = original.getFalconPushInterval();
  }

  /**
   * Create a copy of {@literal options}
   *
   * @param options the original
   * @return A new instance of {@link ClientOptions} containing the values of {@literal options}
   */
  public static ClientOptions copyOf(ClientOptions options) {
    return new ClientOptions(options);
  }

  /**
   * Returns a new {@link ClientOptions.Builder} to construct {@link ClientOptions}.
   *
   * @return a new {@link ClientOptions.Builder} to construct {@link ClientOptions}.
   */
  public static ClientOptions.Builder builder() {
    return new ClientOptions.Builder();
  }

  /**
   * Create a new instance of {@link ClientOptions} with default settings.
   *
   * @return a new instance of {@link ClientOptions} with default settings
   */
  public static ClientOptions create() {
    return builder().build();
  }

  @Override
  public boolean equals(Object options) {
    if (this == options) {
      return true;
    }
    if (options instanceof ClientOptions) {
      ClientOptions clientOptions = (ClientOptions) options;
      if (this.metaServers.equals(clientOptions.metaServers)
          && this.operationTimeout == clientOptions.operationTimeout
          && this.asyncWorkers == clientOptions.asyncWorkers
          && this.enablePerfCounter == clientOptions.enablePerfCounter
          && this.falconPushInterval == clientOptions.falconPushInterval) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "ClientOptions{"
        + "metaServers='"
        + metaServers
        + '\''
        + ", operationTimeout(ms)="
        + operationTimeout.toMillis()
        + ", asyncWorkers="
        + asyncWorkers
        + ", enablePerfCounter="
        + enablePerfCounter
        + ", falconPerfCounterTags='"
        + falconPerfCounterTags
        + '\''
        + ", falconPushInterval(s)="
        + falconPushInterval.getSeconds()
        + '}';
  }

  public static class Builder {
    private String metaServers = DEFAULT_META_SERVERS;
    private Duration operationTimeout = DEFAULT_OPERATION_TIMEOUT;
    private int asyncWorkers = DEFAULT_ASYNC_WORKERS;
    private boolean enablePerfCounter = DEFAULT_ENABLE_PERF_COUNTER;
    private String falconPerfCounterTags = DEFAULT_FALCON_PERF_COUNTER_TAGS;
    private Duration falconPushInterval = DEFAULT_FALCON_PUSH_INTERVAL;

    protected Builder() {}

    public Builder metaServers(String metaServers) {
      this.metaServers = metaServers;
      return this;
    }

    public Builder operationTimeout(Duration operationTimeout) {
      this.operationTimeout = operationTimeout;
      return this;
    }

    public Builder asyncWorkers(int asyncWorkers) {
      this.asyncWorkers = asyncWorkers;
      return this;
    }

    public Builder enablePerfCounter(boolean enablePerfCounter) {
      this.enablePerfCounter = enablePerfCounter;
      return this;
    }

    public Builder falconPerfCounterTags(String falconPerfCounterTags) {
      this.falconPerfCounterTags = falconPerfCounterTags;
      return this;
    }

    public Builder falconPushInterval(Duration falconPushInterval) {
      this.falconPushInterval = falconPushInterval;
      return this;
    }

    /**
     * Create a new instance of {@link ClientOptions}.
     *
     * @return new instance of {@link ClientOptions}
     */
    public ClientOptions build() {
      return new ClientOptions(this);
    }
  }

  /**
   * Returns a builder to create new {@link ClientOptions} whose settings are replicated from the
   * current {@link ClientOptions}.
   *
   * @return a {@link ClientOptions.Builder} to create new {@link ClientOptions} whose settings are
   *     replicated from the current {@link ClientOptions}.
   */
  public ClientOptions.Builder mutate() {
    Builder builder = new Builder();
    builder
        .metaServers(getMetaServers())
        .operationTimeout(getOperationTimeout())
        .asyncWorkers(getAsyncWorkers())
        .enablePerfCounter(isEnablePerfCounter())
        .falconPerfCounterTags(getFalconPerfCounterTags())
        .falconPushInterval(getFalconPushInterval());
    return builder;
  }

  public String getMetaServers() {
    return metaServers;
  }

  public Duration getOperationTimeout() {
    return operationTimeout;
  }

  public int getAsyncWorkers() {
    return asyncWorkers;
  }

  public boolean isEnablePerfCounter() {
    return enablePerfCounter;
  }

  public String getFalconPerfCounterTags() {
    return falconPerfCounterTags;
  }

  public Duration getFalconPushInterval() {
    return falconPushInterval;
  }
}

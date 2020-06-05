// Copyright (c) 2019, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.client;

import java.time.Duration;

/**
 * Client Options to control the behavior of {@link PegasusClientInterface}.
 *
 * <p>To create a new instance with default settings:
 *
 * <pre>{@code
 * ClientOptions.create();
 * }</pre>
 *
 * To customize the settings:
 *
 * <pre>{@code
 * ClientOptions opts =
 *      ClientOptions.builder()
 *          .metaServers("127.0.0.1:34601,127.0.0.1:34602,127.0.0.1:34603")
 *          .operationTimeout(Duration.ofMillis(1000))
 *          .asyncWorkers(4)
 *          .enablePerfCounter(false)
 *          .perfCounterTags("")
 *          .falconPushInterval(Duration.ofSeconds(10))
 *          .metaQueryTimeout(Duration.ofMillis(5000))
 *          .build();
 * }</pre>
 */
public class ClientOptions {

  public static final String DEFAULT_META_SERVERS =
      "127.0.0.1:34601,127.0.0.1:34602,127.0.0.1:34603";
  public static final Duration DEFAULT_OPERATION_TIMEOUT = Duration.ofMillis(1000);
  public static final int DEFAULT_ASYNC_WORKERS = Runtime.getRuntime().availableProcessors();
  public static final boolean DEFAULT_ENABLE_PERF_COUNTER = true;
  public static final String DEFAULT_PERF_COUNTER_TYPE = "falcon";
  public static final String DEFAULT_PERF_COUNTER_TAGS = "";
  public static final Duration DEFAULT_FALCON_PUSH_INTERVAL = Duration.ofSeconds(10);
  public static final boolean DEFAULT_ENABLE_WRITE_LIMIT = true;
  public static final Duration DEFAULT_META_QUERY_TIMEOUT = Duration.ofMillis(5000);

  private final String metaServers;
  private final Duration operationTimeout;
  private final int asyncWorkers;
  private final boolean enablePerfCounter;
  private final String perfCounterType;
  private final String perfCounterTags;
  private final Duration falconPushInterval;
  private final boolean enableWriteLimit;
  private final Duration metaQueryTimeout;

  protected ClientOptions(Builder builder) {
    this.metaServers = builder.metaServers;
    this.operationTimeout = builder.operationTimeout;
    this.asyncWorkers = builder.asyncWorkers;
    this.enablePerfCounter = builder.enablePerfCounter;
    this.perfCounterType = builder.perfCounterType;
    this.perfCounterTags = builder.perfCounterTags;
    this.falconPushInterval = builder.falconPushInterval;
    this.enableWriteLimit = builder.enableWriteLimit;
    this.metaQueryTimeout = builder.metaQueryTimeout;
  }

  protected ClientOptions(ClientOptions original) {
    this.metaServers = original.getMetaServers();
    this.operationTimeout = original.getOperationTimeout();
    this.asyncWorkers = original.getAsyncWorkers();
    this.enablePerfCounter = original.isEnablePerfCounter();
    this.perfCounterType = original.perfCounterType;
    this.perfCounterTags = original.getPerfCounterTags();
    this.falconPushInterval = original.getFalconPushInterval();
    this.enableWriteLimit = original.isWriteLimitEnabled();
    this.metaQueryTimeout = original.getMetaQueryTimeout();
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
      return this.metaServers.equals(clientOptions.metaServers)
          && this.operationTimeout.toMillis() == clientOptions.operationTimeout.toMillis()
          && this.asyncWorkers == clientOptions.asyncWorkers
          && this.enablePerfCounter == clientOptions.enablePerfCounter
          && this.perfCounterType.equals(clientOptions.perfCounterType)
          && this.perfCounterTags.equals(clientOptions.perfCounterTags)
          && this.falconPushInterval.toMillis() == clientOptions.falconPushInterval.toMillis()
          && this.enableWriteLimit == clientOptions.enableWriteLimit
          && this.metaQueryTimeout.toMillis() == clientOptions.metaQueryTimeout.toMillis();
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
        + ", perfCounterType="
        + enablePerfCounter
        + ", perfCounterTags='"
        + perfCounterType
        + ", enablePerfCounter="
        + perfCounterTags
        + '\''
        + ", falconPushInterval(s)="
        + falconPushInterval.getSeconds()
        + ",enableWriteLimit="
        + enableWriteLimit
        + ", metaQueryTimeout(ms)="
        + metaQueryTimeout.toMillis()
        + '}';
  }

  /** Builder for {@link ClientOptions}. */
  public static class Builder {
    private String metaServers = DEFAULT_META_SERVERS;
    private Duration operationTimeout = DEFAULT_OPERATION_TIMEOUT;
    private int asyncWorkers = DEFAULT_ASYNC_WORKERS;
    private boolean enablePerfCounter = DEFAULT_ENABLE_PERF_COUNTER;
    private String perfCounterType = DEFAULT_PERF_COUNTER_TYPE;
    private String perfCounterTags = DEFAULT_PERF_COUNTER_TAGS;
    private Duration falconPushInterval = DEFAULT_FALCON_PUSH_INTERVAL;
    private boolean enableWriteLimit = DEFAULT_ENABLE_WRITE_LIMIT;
    private Duration metaQueryTimeout = DEFAULT_META_QUERY_TIMEOUT;

    protected Builder() {}

    /**
     * The list of meta server addresses, separated by commas, See {@link #DEFAULT_META_SERVERS}.
     *
     * @param metaServers must not be {@literal null} or empty.
     * @return {@code this}
     */
    public Builder metaServers(String metaServers) {
      this.metaServers = metaServers;
      return this;
    }

    /**
     * The timeout for failing to finish an operation. Defaults to {@literal 1000ms}, see {@link
     * #DEFAULT_OPERATION_TIMEOUT}.
     *
     * @param operationTimeout operationTimeout
     * @return {@code this}
     */
    public Builder operationTimeout(Duration operationTimeout) {
      this.operationTimeout = operationTimeout;
      return this;
    }

    /**
     * The number of background worker threads. Internally it is the number of Netty NIO threads for
     * handling RPC events between client and Replica Servers. Defaults to {@literal 4}, see {@link
     * #DEFAULT_ASYNC_WORKERS}.
     *
     * @param asyncWorkers asyncWorkers thread number
     * @return {@code this}
     */
    public Builder asyncWorkers(int asyncWorkers) {
      this.asyncWorkers = asyncWorkers;
      return this;
    }

    /**
     * Whether to enable performance statistics. If true, the client will periodically report
     * metrics to local falcon agent (if set falcon as monitoring system) or open prometheus
     * collector http server. Defaults to {@literal true}, see {@link #DEFAULT_ENABLE_PERF_COUNTER}.
     *
     * @param enablePerfCounter enablePerfCounter
     * @return {@code this}
     */
    public Builder enablePerfCounter(boolean enablePerfCounter) {
      this.enablePerfCounter = enablePerfCounter;
      return this;
    }

    /**
     * set the perf-counter type, now only support falcon and prometheus, Defaults to {@literal
     * falcon}, see {@link #DEFAULT_PERF_COUNTER_TYPE}
     *
     * @param perfCounterType perfCounterType
     * @return this
     */
    public Builder perfCounterType(String perfCounterType) {
      this.perfCounterType = perfCounterType;
      return this;
    }

    /**
     * Additional tags for falcon metrics. For example:
     * "cluster=c3srv-ad,job=recommend-service-history". Defaults to empty string, see {@link
     * #DEFAULT_PERF_COUNTER_TAGS}.
     *
     * @param perfCounterTags perfCounterTags
     * @return {@code this}
     */
    public Builder perfCounterTags(String perfCounterTags) {
      this.perfCounterTags = perfCounterTags;
      return this;
    }

    /**
     * The interval to report metrics to local falcon agent(if set falcon as monitor system).
     * Defaults to {@literal 10s}, see {@link #DEFAULT_FALCON_PUSH_INTERVAL}.
     *
     * @param falconPushInterval falconPushInterval
     * @return {@code this}
     */
    public Builder falconPushInterval(Duration falconPushInterval) {
      this.falconPushInterval = falconPushInterval;
      return this;
    }

    /**
     * whether to enable write limit . if true, exceed the threshold set will throw exception, See
     * {@linkplain com.xiaomi.infra.pegasus.tools.WriteLimiter WriteLimiter}. Defaults to {@literal
     * true}, see {@link #DEFAULT_ENABLE_WRITE_LIMIT}
     *
     * @param enableWriteLimit enableWriteLimit
     * @return {@code this}
     */
    public Builder enableWriteLimit(boolean enableWriteLimit) {
      this.enableWriteLimit = enableWriteLimit;
      return this;
    }

    /**
     * The timeout for query meta server. Defaults to {@literal 5000ms}, see {@link
     * #DEFAULT_META_QUERY_TIMEOUT}.
     *
     * @param metaQueryTimeout metaQueryTimeout
     * @return {@code this}
     */
    public Builder metaQueryTimeout(Duration metaQueryTimeout) {
      this.metaQueryTimeout = metaQueryTimeout;
      return this;
    }

    /**
     * Create a new instance of {@link ClientOptions}.
     *
     * @return new instance of {@link ClientOptions}.
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
        .perfCounterType(getPerfCounterType())
        .perfCounterTags(getPerfCounterTags())
        .falconPushInterval(getFalconPushInterval())
        .enableWriteLimit(isWriteLimitEnabled())
        .metaQueryTimeout(getMetaQueryTimeout());
    return builder;
  }

  /**
   * The list of meta server addresses, separated by commas.
   *
   * @return the list of meta server addresses.
   */
  public String getMetaServers() {
    return metaServers;
  }

  /**
   * The timeout for failing to finish an operation. Defaults to {@literal 1000ms}.
   *
   * @return the timeout for failing to finish an operation.
   */
  public Duration getOperationTimeout() {
    return operationTimeout;
  }

  /**
   * The number of background worker threads. Internally it is the number of Netty NIO threads for
   * handling RPC events between client and Replica Servers. Defaults to {@literal 4}.
   *
   * @return The number of background worker threads.
   */
  public int getAsyncWorkers() {
    return asyncWorkers;
  }

  /**
   * Whether to enable performance statistics. If true, the client will periodically report metrics
   * to local falcon agent (if set falcon as monitoring system) or open prometheus collector http
   * server. Defaults to {@literal true}.
   *
   * @return whether to enable performance statistics.
   */
  public boolean isEnablePerfCounter() {
    return enablePerfCounter;
  }

  /**
   * get perf-counter type, now only support falcon and prometheus
   *
   * @return perf-counter type
   */
  public String getPerfCounterType() {
    return perfCounterType;
  }

  /**
   * Additional tags for metrics. Defaults to empty string.
   *
   * @return additional tags for falcon metrics.
   */
  public String getPerfCounterTags() {
    return perfCounterTags;
  }

  /**
   * The interval to report metrics to local falcon agent(if set falcon as monitor system). Defaults
   * to {@literal 10s}.
   *
   * @return the interval to report metrics to local falcon agent.
   */
  public Duration getFalconPushInterval() {
    return falconPushInterval;
  }

  /**
   * whether to enable write limit. if true, exceed the threshold set will throw exception, See
   * {@linkplain com.xiaomi.infra.pegasus.tools.WriteLimiter WriteLimiter}. Defaults to {@literal
   * true}, See {@link #DEFAULT_ENABLE_WRITE_LIMIT}
   *
   * @return whether to enable write size limit
   */
  public boolean isWriteLimitEnabled() {
    return enableWriteLimit;
  }

  /**
   * The timeout for query meta server. Defaults to {@literal 5000ms}.
   *
   * @return the timeout for query meta server.
   */
  public Duration getMetaQueryTimeout() {
    return metaQueryTimeout;
  }
}

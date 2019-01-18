// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.rpc;

import com.xiaomi.infra.pegasus.thrift.TException;
import com.xiaomi.infra.pegasus.rpc.async.ClusterManager;

import java.util.Properties;

public abstract class Cluster {
    public static final int SOCK_TIMEOUT = 1000;
    public static final int QUERY_META_TIMEOUT = 1000;

    public static final String PEGASUS_META_SERVERS_KEY = "meta_servers";

    public static final String PEGASUS_OPERATION_TIMEOUT_KEY = "operation_timeout";
    public static final String PEGASUS_OPERATION_TIMEOUT_DEF = "1000";

    public static final String PEGASUS_ASYNC_WORKERS_KEY = "async_workers";
    public static final String PEGASUS_ASYNC_WORKERS_DEF = "4";

    public static final String PEGASUS_ENABLE_PERF_COUNTER_KEY = "enable_perf_counter";
    public static final String PEGASUS_ENABLE_PERF_COUNTER_VALUE = "false";

    public static final String PEGASUS_PERF_COUNTER_TAGS_KEY = "perf_counter_tags";
    public static final String PEGASUS_PERF_COUNTER_TAGS_DEF = "";

    public static final String PEGASUS_PUSH_COUNTER_INTERVAL_SECS_KEY = "push_counter_interval_secs";
    public static final String PEGASUS_PUSH_COUNTER_INTERVAL_SECS_DEF = "60";

    public static final String PEGASUS_SERVICE_NAME_KEY = "service_name";
    public static final String PEGASUS_SERVICE_NAME_DEF = "test-server";

    public static final String PEGASUS_SERVICE_FQDN_KEY = "service_fqdn";
    public static final String PEGASUS_SERVICE_FQDN_DEF = "myhost";

    public static final String PEGASUS_OPEN_AUTH_KEY = "open_auth";
    public static final String PEGASUS_OPEN_AUTH_DEF = "false";

    public static final String PEGASUS_JAAS_CONF_KEY = "jaas_conf";
    public static final String PEGASUS_JAAS_CONF_DEF = "configuration/pegasus_jaas.conf";

    public static Cluster createCluster(Properties config) throws IllegalArgumentException {
        int operatorTimeout = Integer.parseInt(config.getProperty(
                PEGASUS_OPERATION_TIMEOUT_KEY, PEGASUS_OPERATION_TIMEOUT_DEF));
        String metaList = config.getProperty(PEGASUS_META_SERVERS_KEY);
        if (metaList == null) {
            throw new IllegalArgumentException("no property set: " + PEGASUS_META_SERVERS_KEY);
        }
        metaList = metaList.trim();
        if (metaList.isEmpty()) {
            throw new IllegalArgumentException("invalid property: " + PEGASUS_META_SERVERS_KEY);
        }
        String[] address = metaList.split(",");

        int asyncWorkers = Integer.parseInt(config.getProperty(
                PEGASUS_ASYNC_WORKERS_KEY, PEGASUS_ASYNC_WORKERS_DEF));
        ClusterManager.Builder builder = new ClusterManager.Builder(operatorTimeout, asyncWorkers, address);

        boolean enablePerfCounter = Boolean.parseBoolean(config.getProperty(
                PEGASUS_ENABLE_PERF_COUNTER_KEY, PEGASUS_ENABLE_PERF_COUNTER_VALUE));
        if (enablePerfCounter) {
            String perfCounterTags = config.getProperty(
                    PEGASUS_PERF_COUNTER_TAGS_KEY, PEGASUS_PERF_COUNTER_TAGS_DEF);
            int pushIntervalSecs = Integer.parseInt(config.getProperty(
                    PEGASUS_PUSH_COUNTER_INTERVAL_SECS_KEY,
                    PEGASUS_PUSH_COUNTER_INTERVAL_SECS_DEF
            ));
            builder.enableCounter(perfCounterTags, pushIntervalSecs);
        }

        boolean needAuth = Boolean.parseBoolean(config.getProperty(PEGASUS_OPEN_AUTH_KEY, PEGASUS_OPEN_AUTH_DEF));

        if (needAuth) {
            String serviceName = config.getProperty(PEGASUS_SERVICE_NAME_KEY, PEGASUS_SERVICE_NAME_DEF);
            String serviceFqdn = config.getProperty(PEGASUS_SERVICE_FQDN_KEY, PEGASUS_SERVICE_FQDN_DEF);
            String jaasConfPath = config.getProperty(PEGASUS_JAAS_CONF_KEY, PEGASUS_JAAS_CONF_DEF);
            if (jaasConfPath != null) {
                System.setProperty("java.security.auth.login.config", jaasConfPath);
            }
            builder.openAuth(serviceName, serviceFqdn);
        }
        return builder.build();
    }

    public abstract String[] getMetaList();

    public abstract Table openTable(String name, KeyHasher function) throws ReplicationException, TException;

    public abstract void close();
}

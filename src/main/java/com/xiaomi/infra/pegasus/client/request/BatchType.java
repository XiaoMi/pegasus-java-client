package com.xiaomi.infra.pegasus.client.request;

public enum  BatchType {
    set, multiSet,
    Get, multiGet, rangeGet,
    Delete, multiDelete, rangeDelete,
    ttl, exist, incr,
    checkAndSet, checkAndMutate
}

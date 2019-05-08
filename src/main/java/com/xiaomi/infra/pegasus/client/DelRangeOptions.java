package com.xiaomi.infra.pegasus.client;

public class DelRangeOptions {
  public boolean startInclusive = true; // if the startSortKey is included
  public boolean stopInclusive = false; // if the stopSortKey is included
  public FilterType sortKeyFilterType = FilterType.FT_NO_FILTER; // filter type for sort key
  public byte[] sortKeyFilterPattern = null; // filter pattern for sort key

  public DelRangeOptions() {}

  public DelRangeOptions(DelRangeOptions o) {
    startInclusive = o.startInclusive;
    stopInclusive = o.stopInclusive;
    sortKeyFilterType = o.sortKeyFilterType;
    sortKeyFilterPattern = o.sortKeyFilterPattern;
  }
}

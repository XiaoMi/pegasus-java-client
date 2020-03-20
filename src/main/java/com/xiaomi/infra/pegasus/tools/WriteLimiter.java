package com.xiaomi.infra.pegasus.tools;

import com.xiaomi.infra.pegasus.apps.mutate;
import com.xiaomi.infra.pegasus.client.Mutations;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public class WriteLimiter {
  private static final int SINGLE_KEY_SIZE = 1024;
  private static final int SINGLE_VALUE_SIZE = 400 * 1024;
  private static final int MULTI_VALUE_COUNT = 1000;
  private static final int MULTI_VALUE_SIZE = 1024 * 1024;

  private boolean isEnable;

  public WriteLimiter(boolean isEnable) {
    this.isEnable = isEnable;
  }

  public void validateSet(byte[] hashKey, byte[] sortKey, byte[] value) throws Exception {
    if (!isEnable) {
      return;
    }

    checkSingleHashKey(hashKey);
    checkSingleSortKey(hashKey, sortKey);
    checkSingleValue(hashKey, sortKey, value);
  }

  public void validateMultiSet(byte[] hashKey, List<Pair<byte[], byte[]>> values) throws Exception {
    if (!isEnable) {
      return;
    }

    checkSingleHashKey(hashKey);
    checkMultiValueCount(hashKey, values.size());

    int valuesLength = 0;
    for (Pair<byte[], byte[]> value : values) {
      checkSingleSortKey(hashKey, value.getLeft());
      checkSingleValue(hashKey, value.getLeft(), value.getRight());
      valuesLength += value.getRight().length;
      checkMultiValueSize(hashKey, valuesLength);
    }
  }

  public void validateCheckAndMutate(byte[] hashKey, Mutations mutations) throws Exception {
    if (!isEnable) {
      return;
    }

    checkSingleHashKey(hashKey);
    checkMultiValueCount(hashKey, mutations.getMutations().size());

    int valuesLength = 0;
    for (mutate mu : mutations.getMutations()) {
      checkSingleSortKey(hashKey, mu.sort_key.data);
      checkSingleValue(hashKey, mu.sort_key.data, mu.value.data);
      valuesLength += mu.value.data.length;
      checkMultiValueSize(hashKey, valuesLength);
    }
  }

  private void checkSingleHashKey(byte[] hashKey) throws Exception {
    if (hashKey.length > SINGLE_KEY_SIZE) {
      throw new Exception(
          "Exceed the hashKey length threshold = "
              + SINGLE_KEY_SIZE
              + ",hashKeyLength = "
              + hashKey.length
              + ",hashKey(head 100) = "
              + new String(hashKey).substring(0, 100));
    }
  }

  private void checkSingleSortKey(byte[] hashKey, byte[] sortKey) throws Exception {
    if (sortKey.length > SINGLE_KEY_SIZE) {
      throw new Exception(
          "Exceed the sort key length threshold = "
              + SINGLE_KEY_SIZE
              + ",sortKeyLength = "
              + sortKey.length
              + ",hashKey(head 100) = "
              + new String(hashKey).substring(0, 100)
              + ",sortKey(head 100) = "
              + new String(sortKey).substring(0, 100));
    }
  }

  private void checkSingleValue(byte[] hashKey, byte[] sortKey, byte[] value) throws Exception {
    if (value.length > SINGLE_VALUE_SIZE) {
      throw new Exception(
          "Exceed the value length threshold = "
              + SINGLE_VALUE_SIZE
              + " valueLength = "
              + value.length
              + ",hashKey(head 100) = "
              + new String(hashKey).substring(0, 100)
              + ",sortKey(head 100) = "
              + new String(value).substring(0, 100));
    }
  }

  private void checkMultiValueCount(byte[] hashKey, int count) throws Exception {
    if (count > MULTI_VALUE_COUNT) {
      throw new Exception(
          "Exceed the value count threshold = "
              + MULTI_VALUE_COUNT
              + ",hashKey(head 100) = "
              + new String(hashKey).substring(0, 100));
    }
  }

  private void checkMultiValueSize(byte[] hashKey, int length) throws Exception {
    if (length > MULTI_VALUE_SIZE) {
      throw new Exception(
          "Exceed the multi value length threshold = "
              + MULTI_VALUE_SIZE
              + ",hashKey(head 100) = "
              + new String(hashKey).substring(0, 100));
    }
  }
}

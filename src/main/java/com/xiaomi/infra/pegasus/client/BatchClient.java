package com.xiaomi.infra.pegasus.client;

import static com.xiaomi.infra.pegasus.client.request.BatchType.Get;

import com.xiaomi.infra.pegasus.client.request.BatchType;
import com.xiaomi.infra.pegasus.client.request.Get;
import com.xiaomi.infra.pegasus.client.request.Request;
import com.xiaomi.infra.pegasus.client.request.Response;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class BatchClient {
  private final BatchType batchType;

  private final PegasusTableInterface table;
  private final int timeout;

  private List<Request> requests = new ArrayList<>();

  public BatchClient(PegasusTableInterface table, BatchType batchType, int timeout) {
    this.batchType = batchType;
    this.table = table;
    this.timeout = timeout;
  }

  public BatchClient add(Request request) {
    requests.add(request);
    return this;
  }

  public void commit(List<Response> responses) {
    assert (!requests.isEmpty());
    responses.clear();
    for (Request request : requests) {
      switch (batchType) {
        case Get:
          {
            List<byte[]> result = new ArrayList<>();
            Get get = (Get) request;
            FutureGroup<byte[]> futureGroup = new FutureGroup<>(requests.size());
            futureGroup.add(table.asyncGet(get.hashKey, get.sortKey, timeout));
            futureGroup.waitAllCompleteOrOneFail(result, timeout);
          }
      }
    }
  }

  public int commitWaitAllComplete(List<Pair<PException, Response>> responses) {
    assert (!requests.isEmpty());
    try {
      Field field = BatchClient.class.getDeclaredField("requests");
      Class<?> fieldClass = field.getType();
      Type fieldType = field.getGenericType();
      if (fieldType instanceof ParameterizedType) {
        Type rawType = ((ParameterizedType) fieldType).getRawType();
      }

    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
  }
}

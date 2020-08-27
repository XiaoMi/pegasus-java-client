package com.xiaomi.infra.pegasus.tools.interceptor;

import com.xiaomi.infra.pegasus.apps.key_value;
import com.xiaomi.infra.pegasus.apps.mutate;
import com.xiaomi.infra.pegasus.base.error_code.error_types;
import com.xiaomi.infra.pegasus.client.PException;
import com.xiaomi.infra.pegasus.operator.rrdb_check_and_mutate_operator;
import com.xiaomi.infra.pegasus.operator.rrdb_check_and_set_operator;
import com.xiaomi.infra.pegasus.operator.rrdb_get_operator;
import com.xiaomi.infra.pegasus.operator.rrdb_multi_get_operator;
import com.xiaomi.infra.pegasus.operator.rrdb_multi_put_operator;
import com.xiaomi.infra.pegasus.operator.rrdb_put_operator;
import com.xiaomi.infra.pegasus.operator.rrdb_scan_operator;
import com.xiaomi.infra.pegasus.rpc.async.ClientRequestRound;
import com.xiaomi.infra.pegasus.rpc.async.TableHandler;
import com.xiaomi.infra.pegasus.tools.ZstdWrapper;
import java.util.List;

public class CompressInterceptor implements TableInterceptor {
  private boolean isOpen;

  public CompressInterceptor(boolean isOpen) {
    this.isOpen = isOpen;
  }

  @Override
  public void interceptBefore(ClientRequestRound clientRequestRound, TableHandler tableHandler)
      throws PException {
    if (!isOpen) {
      return;
    }
    tryCompress(clientRequestRound);
  }

  @Override
  public void interceptAfter(
      ClientRequestRound clientRequestRound, error_types errno, TableHandler tableHandler)
      throws PException {
    if (errno != error_types.ERR_OK || !isOpen) {
      return;
    }
    tryDecompress(clientRequestRound);
  }

  private void tryCompress(ClientRequestRound clientRequestRound) throws PException {
    String operatorName = clientRequestRound.getOperator().name();
    switch (operatorName) {
      case "put":
        {
          rrdb_put_operator operator = (rrdb_put_operator) clientRequestRound.getOperator();
          operator.get_request().value.data =
              ZstdWrapper.compress(operator.get_request().value.data);
        }
      case "multi_put":
        {
          rrdb_multi_put_operator operator =
              (rrdb_multi_put_operator) clientRequestRound.getOperator();
          List<key_value> kvs = operator.get_request().kvs;
          for (key_value kv : kvs) {
            kv.value.data = ZstdWrapper.compress(kv.value.data);
          }
          break;
        }
      case "check_and_set":
        {
          rrdb_check_and_set_operator operator =
              (rrdb_check_and_set_operator) clientRequestRound.getOperator();
          operator.get_request().set_value.data =
              ZstdWrapper.compress(operator.get_request().set_value.data);
          break;
        }
      case "check_and_mutate":
        {
          rrdb_check_and_mutate_operator operator =
              (rrdb_check_and_mutate_operator) clientRequestRound.getOperator();
          List<mutate> mutates = operator.get_request().mutate_list;
          for (mutate mu : mutates) {
            mu.value.data = ZstdWrapper.compress(mu.value.data);
          }
        }
      default:
        throw new PException("unsupported operator = " + operatorName);
    }
  }

  private void tryDecompress(ClientRequestRound clientRequestRound) throws PException {
    String operatorName = clientRequestRound.getOperator().name();
    switch (operatorName) {
      case "get":
        {
          ZstdWrapper.tryDecompress(
              ((rrdb_get_operator) clientRequestRound.getOperator()).get_response().value.data);
          break;
        }
      case "multi_get":
        {
          rrdb_multi_get_operator operator =
              (rrdb_multi_get_operator) clientRequestRound.getOperator();
          List<key_value> kvs = operator.get_response().kvs;
          for (key_value kv : kvs) {
            kv.value.data = ZstdWrapper.tryDecompress(kv.value.data);
          }
          break;
        }
      case "scan":
        {
          rrdb_scan_operator operator = (rrdb_scan_operator) clientRequestRound.getOperator();
          List<key_value> kvs = operator.get_response().kvs;
          for (key_value kv : kvs) {
            kv.value.data = ZstdWrapper.tryDecompress(kv.value.data);
          }
          break;
        }
      default:
        throw new PException("unsupported operator = " + operatorName);
    }
  }
}

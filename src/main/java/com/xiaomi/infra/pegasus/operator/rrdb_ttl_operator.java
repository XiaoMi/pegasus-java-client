// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.operator;

import com.xiaomi.infra.pegasus.apps.rrdb;
import com.xiaomi.infra.pegasus.apps.ttl_response;
import com.xiaomi.infra.pegasus.base.blob;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.protocol.TMessageType;

public class rrdb_ttl_operator extends read_operator {
  public rrdb_ttl_operator(
      com.xiaomi.infra.pegasus.base.gpid gpid, String tableName, blob request, long partitionHash) {
    super(gpid, tableName, partitionHash);
    this.request = request;
  }

  public String name() {
    return "ttl";
  }

  public void send_data(org.apache.thrift.protocol.TProtocol oprot, int seqid) throws TException {
    TMessage msg = new TMessage("RPC_RRDB_RRDB_TTL", TMessageType.CALL, seqid);
    oprot.writeMessageBegin(msg);
    rrdb.get_args get_args = new rrdb.get_args(request);
    get_args.write(oprot);
    oprot.writeMessageEnd();
  }

  public void recv_data(org.apache.thrift.protocol.TProtocol iprot) throws TException {
    rrdb.ttl_result result = new rrdb.ttl_result();
    result.read(iprot);
    if (result.isSetSuccess()) resp = result.success;
    else
      throw new org.apache.thrift.TApplicationException(
          org.apache.thrift.TApplicationException.MISSING_RESULT, "get ttl failed: unknown result");
  }

  public ttl_response get_response() {
    return resp;
  }

  private blob request;
  private ttl_response resp;
}

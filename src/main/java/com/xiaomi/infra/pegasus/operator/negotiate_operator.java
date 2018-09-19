// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.operator;

import com.xiaomi.infra.pegasus.apps.negotiation_message;
import com.xiaomi.infra.pegasus.apps.security;
import com.xiaomi.infra.pegasus.base.gpid;
import com.xiaomi.infra.pegasus.thrift.TException;
import com.xiaomi.infra.pegasus.thrift.protocol.TMessage;
import com.xiaomi.infra.pegasus.thrift.protocol.TMessageType;

public class negotiate_operator extends client_operator {
    public negotiate_operator(negotiation_message request) {
        super(new gpid(), ""); // TODO HW gpid and tableName needless
        this.request = request;
    }

    public String name() {
        return "negotiate";
    }

    public void send_data(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol oprot, int seqid)
            throws TException {
        TMessage msg = new TMessage("RPC_NEGOTIATION", TMessageType.CALL, seqid);
        oprot.writeMessageBegin(msg);
        security.negotiate_args get_args = new security.negotiate_args(request);
        get_args.write(oprot);
        oprot.writeMessageEnd();
    }

    public void recv_data(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol iprot)
            throws TException {
        security.negotiate_result result = new security.negotiate_result();
        result.read(iprot);
        if (result.isSetSuccess()) resp = result.success;
        else
            throw new com.xiaomi.infra.pegasus.thrift.TApplicationException(
                    com.xiaomi.infra.pegasus.thrift.TApplicationException.MISSING_RESULT,
                    "get failed: unknown result");
    }

    public negotiation_message get_response() {
        return resp;
    }

    private negotiation_message request;
    private negotiation_message resp;
}

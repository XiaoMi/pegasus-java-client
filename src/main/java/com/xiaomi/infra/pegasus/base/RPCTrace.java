package com.xiaomi.infra.pegasus.base;

import javafx.util.Pair;

public class RPCTrace {

  //<K,V> = <ThreadName, value>
  public Pair<String, String> rpc_id;
  public Pair<String, String> rpc_seq_id;
  public Pair<String, String> rpc_operation;
  public Pair<String, String> rpc_hash_key;
  public Pair<String, String> rpc_sort_key;
  public Pair<String, String> rpc_value;
  public Pair<String, String> rpc_remote_address;

  //<K,V> = <ThreadName, Time>
  public Pair<String, Long> PegasusTable_asyncRequest;
  public Pair<String, Long> TableHandler_asyncOperate;
  public Pair<String, Long> TableHandler_call;
  public Pair<String, Long> ReplicaSession_doConnected;
  public Pair<String, Long> ReplicaSession_doConnected_operationComplete;
  public Pair<String, Long> ReplicaSession_asyncSend;
  public Pair<String, Long> ReplicaSession_write;
  public Pair<String, Long> client_operator_encode_send_data;
  public Pair<String, Long> ReplicaSession_write_operationComplete;
  public Pair<String, Long> client_operator_decode_recv_data;
  public Pair<String, Long> ReplicaSession_tryNotifyWithSequenceID;
  public Pair<String, Long> TableHandler_call_onRpcReply;
  public Pair<String, Long> TableHandler_call_onRpcReply_tryDelayCall;
  public Pair<String, Long> TableHandler_call_onRpcReply_thisRoundCompletion;
  public Pair<String, Long> TableHandler_call_onRpcReply_thisRoundCompletion_onCompletion;

  public long sendTime;
  public long netWorkTime;
  public long recvTime;

  public void setRpc_id(Pair<String, String> rpc_id) {
    this.rpc_id = rpc_id;
  }

  public void setRpc_seq_id(Pair<String, String> rpc_seq_id) {
    this.rpc_seq_id = rpc_seq_id;
  }

  public void setRpc_operation(Pair<String, String> rpc_operation) {
    this.rpc_operation = rpc_operation;
  }

  public void setRpc_hash_key(Pair<String, String> rpc_hash_key) {
    this.rpc_hash_key = rpc_hash_key;
  }

  public void setRpc_sort_key(Pair<String, String> rpc_sort_key) {
    this.rpc_sort_key = rpc_sort_key;
  }

  public void setRpc_value(Pair<String, String> rpc_value) {
    this.rpc_value = rpc_value;
  }

  public void setRpc_remote_address(Pair<String, String> rpc_remote_address) {
    this.rpc_remote_address = rpc_remote_address;
  }

  public void setPegasusTable_asyncRequest(Pair<String, Long> pegasusTable_asyncRequest) {
    PegasusTable_asyncRequest = pegasusTable_asyncRequest;
  }

  public void setTableHandler_asyncOperate(Pair<String, Long> tableHandler_asyncOperate) {
    TableHandler_asyncOperate = tableHandler_asyncOperate;
  }

  public void setTableHandler_call(Pair<String, Long> tableHandler_call) {
    TableHandler_call = tableHandler_call;
  }

  public void setReplicaSession_doConnected(Pair<String, Long> replcaSession_doConnected) {
    ReplicaSession_doConnected = replcaSession_doConnected;
  }

  public void setReplicaSession_doConnected_operationComplete(
      Pair<String, Long> replcaSession_doConnected_operationComplete) {
    ReplicaSession_doConnected_operationComplete = replcaSession_doConnected_operationComplete;
  }

  public void setReplicaSession_asyncSend(Pair<String, Long> replcaSession_asyncSend) {
    ReplicaSession_asyncSend = replcaSession_asyncSend;
  }

  public void setReplicaSession_write(Pair<String, Long> replcaSession_write) {
    ReplicaSession_write = replcaSession_write;
  }

  public void setClient_operator_encode_send_data(
      Pair<String, Long> client_operator_encode_send_data) {
    this.client_operator_encode_send_data = client_operator_encode_send_data;
  }

  public void setClient_operator_decode_recv_data(
      Pair<String, Long> client_operator_decode_recv_data) {
    this.client_operator_decode_recv_data = client_operator_decode_recv_data;
  }

  public void setReplicaSession_write_operationComplete(
      Pair<String, Long> replcaSession_write_operationComplete) {
    ReplicaSession_write_operationComplete = replcaSession_write_operationComplete;
  }

  public void setReplicaSession_tryNotifyWithSequenceID(
      Pair<String, Long> replcaSession_tryNotifyWithSequenceID) {
    ReplicaSession_tryNotifyWithSequenceID = replcaSession_tryNotifyWithSequenceID;
  }

  public void setTableHandler_call_onRpcReply(Pair<String, Long> tableHandler_call_onRpcReply) {
    TableHandler_call_onRpcReply = tableHandler_call_onRpcReply;
  }

  public void setTableHandler_call_onRpcReply_tryDelayCall(
      Pair<String, Long> tableHandler_call_onRpcReply_tryDelayCall) {
    TableHandler_call_onRpcReply_tryDelayCall = tableHandler_call_onRpcReply_tryDelayCall;
  }

  public void setTableHandler_call_onRpcReply_thisRoundCompletion(
      Pair<String, Long> tableHandler_call_onRpcReply_thisRoundCompletion) {
    TableHandler_call_onRpcReply_thisRoundCompletion =
        tableHandler_call_onRpcReply_thisRoundCompletion;
  }

  public void setTableHandler_call_onRpcReply_thisRoundCompletion_onCompletion(
      Pair<String, Long> tableHandler_call_onRpcReply_thisRoundCompletion_onCompletion) {
    TableHandler_call_onRpcReply_thisRoundCompletion_onCompletion =
        tableHandler_call_onRpcReply_thisRoundCompletion_onCompletion;
  }

  public void compute() {
    sendTime = PegasusTable_asyncRequest.getValue() - client_operator_encode_send_data.getValue();
    netWorkTime =
        ReplicaSession_write_operationComplete.getValue()
            - client_operator_encode_send_data.getValue();
    recvTime =
        TableHandler_call_onRpcReply_thisRoundCompletion_onCompletion.getValue()
            - ReplicaSession_write_operationComplete.getValue();
  }
}

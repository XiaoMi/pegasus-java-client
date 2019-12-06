package com.xiaomi.infra.pegasus.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RpcTrace {

  public String rpcId;
  public int rpcTryId;
  public String rpcState;
  public int rpcSeqId;
  public String rpcTable;
  public long rpcTimeOut;
  public String rpcOperation;
  public String rpcRemoteAddress;

  public long startAsyncRequest;
  public long startCall;
  public long startAsyncSend;
  public long startWrite;
  public long writeComplete;
  public long channelRead0;
  public long startTryNotifyError;
  public long onRpcReply;
  public long onCompletion;

  public long allTimeUsed;
  public long asyncRequest2asyncSend;

  // success trace
  public long asyncSend2write;
  public long write2writeComplete;
  public long writeComplete2channelRead0;
  public long channelRead02onCompletion;

  // failed trace
  public long asyncSend2notifyError;
  public long write2notifyError;
  public long notifyError2onCompletion;

  public long call2onRpcReply;
  public boolean isTimeOutTask;

  public RpcTrace(String rpcId, String rpcTable, long startAsyncRequest, long timeUsed) {
    this.rpcId = rpcId;
    this.rpcTable = rpcTable;
    this.startAsyncRequest = startAsyncRequest;
    this.rpcTimeOut = timeUsed;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
        .append("****************")
        .append("rpcOperation:" + rpcOperation)
        .append("rpcRemoteAddress:" + rpcRemoteAddress)
        .append("rpcId:" + rpcId)
        .append("rpcTable:" + rpcTable)
        .append("rpcTimeOut:" + rpcTimeOut)
        .append("rpcSeqId:" + rpcSeqId)
        .append("rpcState:" + rpcState)
        .append("****************")
        .append(startAsyncRequest + " startAsyncRequest")
        .append(allTimeUsed + " allTimeUsed")
        .append(asyncRequest2asyncSend + " asyncRequest2asyncSend")
        .append(asyncSend2write + " asyncSend2write")
        .append(write2writeComplete + " write2writeComplete")
        .append(writeComplete2channelRead0 + " writeComplete2channelRead0")
        .append(channelRead02onCompletion + " channelRead02onCompletion")
        .append(asyncSend2notifyError + " asyncSend2notifyError[isTimerTask=" + isTimeOutTask + "]")
        .append(write2notifyError + " write2notifyError[isTimerTask=" + isTimeOutTask + "]")
        .append(notifyError2onCompletion + " notifyError2onCompletion")
        .append(onCompletion + " onCompletion")
        .toString();
  }
}

// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xiaomi.infra.pegasus.apps;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2018-08-07")
public class meta {

  public interface Iface {

    public com.xiaomi.infra.pegasus.replication.query_cfg_response query_cfg(com.xiaomi.infra.pegasus.replication.query_cfg_request query) throws com.xiaomi.infra.pegasus.thrift.TException;

  }

  public interface AsyncIface {

    public void query_cfg(com.xiaomi.infra.pegasus.replication.query_cfg_request query, com.xiaomi.infra.pegasus.thrift.async.AsyncMethodCallback<com.xiaomi.infra.pegasus.replication.query_cfg_response> resultHandler) throws com.xiaomi.infra.pegasus.thrift.TException;

  }

  public static class Client extends com.xiaomi.infra.pegasus.thrift.TServiceClient implements Iface {
    public static class Factory implements com.xiaomi.infra.pegasus.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol iprot, com.xiaomi.infra.pegasus.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol iprot, com.xiaomi.infra.pegasus.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public com.xiaomi.infra.pegasus.replication.query_cfg_response query_cfg(com.xiaomi.infra.pegasus.replication.query_cfg_request query) throws com.xiaomi.infra.pegasus.thrift.TException
    {
      send_query_cfg(query);
      return recv_query_cfg();
    }

    public void send_query_cfg(com.xiaomi.infra.pegasus.replication.query_cfg_request query) throws com.xiaomi.infra.pegasus.thrift.TException
    {
      query_cfg_args args = new query_cfg_args();
      args.setQuery(query);
      sendBase("query_cfg", args);
    }

    public com.xiaomi.infra.pegasus.replication.query_cfg_response recv_query_cfg() throws com.xiaomi.infra.pegasus.thrift.TException
    {
      query_cfg_result result = new query_cfg_result();
      receiveBase(result, "query_cfg");
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new com.xiaomi.infra.pegasus.thrift.TApplicationException(com.xiaomi.infra.pegasus.thrift.TApplicationException.MISSING_RESULT, "query_cfg failed: unknown result");
    }

  }
  public static class AsyncClient extends com.xiaomi.infra.pegasus.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements com.xiaomi.infra.pegasus.thrift.async.TAsyncClientFactory<AsyncClient> {
      private com.xiaomi.infra.pegasus.thrift.async.TAsyncClientManager clientManager;
      private com.xiaomi.infra.pegasus.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(com.xiaomi.infra.pegasus.thrift.async.TAsyncClientManager clientManager, com.xiaomi.infra.pegasus.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(com.xiaomi.infra.pegasus.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(com.xiaomi.infra.pegasus.thrift.protocol.TProtocolFactory protocolFactory, com.xiaomi.infra.pegasus.thrift.async.TAsyncClientManager clientManager, com.xiaomi.infra.pegasus.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void query_cfg(com.xiaomi.infra.pegasus.replication.query_cfg_request query, com.xiaomi.infra.pegasus.thrift.async.AsyncMethodCallback<com.xiaomi.infra.pegasus.replication.query_cfg_response> resultHandler) throws com.xiaomi.infra.pegasus.thrift.TException {
      checkReady();
      query_cfg_call method_call = new query_cfg_call(query, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class query_cfg_call extends com.xiaomi.infra.pegasus.thrift.async.TAsyncMethodCall<com.xiaomi.infra.pegasus.replication.query_cfg_response> {
      private com.xiaomi.infra.pegasus.replication.query_cfg_request query;
      public query_cfg_call(com.xiaomi.infra.pegasus.replication.query_cfg_request query, com.xiaomi.infra.pegasus.thrift.async.AsyncMethodCallback<com.xiaomi.infra.pegasus.replication.query_cfg_response> resultHandler, com.xiaomi.infra.pegasus.thrift.async.TAsyncClient client, com.xiaomi.infra.pegasus.thrift.protocol.TProtocolFactory protocolFactory, com.xiaomi.infra.pegasus.thrift.transport.TNonblockingTransport transport) throws com.xiaomi.infra.pegasus.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.query = query;
      }

      public void write_args(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot) throws com.xiaomi.infra.pegasus.thrift.TException {
        prot.writeMessageBegin(new com.xiaomi.infra.pegasus.thrift.protocol.TMessage("query_cfg", com.xiaomi.infra.pegasus.thrift.protocol.TMessageType.CALL, 0));
        query_cfg_args args = new query_cfg_args();
        args.setQuery(query);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public com.xiaomi.infra.pegasus.replication.query_cfg_response getResult() throws com.xiaomi.infra.pegasus.thrift.TException {
        if (getState() != com.xiaomi.infra.pegasus.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new java.lang.IllegalStateException("Method call not finished!");
        }
        com.xiaomi.infra.pegasus.thrift.transport.TMemoryInputTransport memoryTransport = new com.xiaomi.infra.pegasus.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_query_cfg();
      }
    }

  }

  public static class Processor<I extends Iface> extends com.xiaomi.infra.pegasus.thrift.TBaseProcessor<I> implements com.xiaomi.infra.pegasus.thrift.TProcessor {
    private static final org.slf4j.Logger _LOGGER = org.slf4j.LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new java.util.HashMap<java.lang.String, com.xiaomi.infra.pegasus.thrift.ProcessFunction<I, ? extends com.xiaomi.infra.pegasus.thrift.TBase>>()));
    }

    protected Processor(I iface, java.util.Map<java.lang.String, com.xiaomi.infra.pegasus.thrift.ProcessFunction<I, ? extends com.xiaomi.infra.pegasus.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> java.util.Map<java.lang.String,  com.xiaomi.infra.pegasus.thrift.ProcessFunction<I, ? extends com.xiaomi.infra.pegasus.thrift.TBase>> getProcessMap(java.util.Map<java.lang.String, com.xiaomi.infra.pegasus.thrift.ProcessFunction<I, ? extends  com.xiaomi.infra.pegasus.thrift.TBase>> processMap) {
      processMap.put("query_cfg", new query_cfg());
      return processMap;
    }

    public static class query_cfg<I extends Iface> extends com.xiaomi.infra.pegasus.thrift.ProcessFunction<I, query_cfg_args> {
      public query_cfg() {
        super("query_cfg");
      }

      public query_cfg_args getEmptyArgsInstance() {
        return new query_cfg_args();
      }

      protected boolean isOneway() {
        return false;
      }

      @Override
      protected boolean handleRuntimeExceptions() {
        return false;
      }

      public query_cfg_result getResult(I iface, query_cfg_args args) throws com.xiaomi.infra.pegasus.thrift.TException {
        query_cfg_result result = new query_cfg_result();
        result.success = iface.query_cfg(args.query);
        return result;
      }
    }

  }

  public static class AsyncProcessor<I extends AsyncIface> extends com.xiaomi.infra.pegasus.thrift.TBaseAsyncProcessor<I> {
    private static final org.slf4j.Logger _LOGGER = org.slf4j.LoggerFactory.getLogger(AsyncProcessor.class.getName());
    public AsyncProcessor(I iface) {
      super(iface, getProcessMap(new java.util.HashMap<java.lang.String, com.xiaomi.infra.pegasus.thrift.AsyncProcessFunction<I, ? extends com.xiaomi.infra.pegasus.thrift.TBase, ?>>()));
    }

    protected AsyncProcessor(I iface, java.util.Map<java.lang.String,  com.xiaomi.infra.pegasus.thrift.AsyncProcessFunction<I, ? extends  com.xiaomi.infra.pegasus.thrift.TBase, ?>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends AsyncIface> java.util.Map<java.lang.String,  com.xiaomi.infra.pegasus.thrift.AsyncProcessFunction<I, ? extends  com.xiaomi.infra.pegasus.thrift.TBase,?>> getProcessMap(java.util.Map<java.lang.String,  com.xiaomi.infra.pegasus.thrift.AsyncProcessFunction<I, ? extends  com.xiaomi.infra.pegasus.thrift.TBase, ?>> processMap) {
      processMap.put("query_cfg", new query_cfg());
      return processMap;
    }

    public static class query_cfg<I extends AsyncIface> extends com.xiaomi.infra.pegasus.thrift.AsyncProcessFunction<I, query_cfg_args, com.xiaomi.infra.pegasus.replication.query_cfg_response> {
      public query_cfg() {
        super("query_cfg");
      }

      public query_cfg_args getEmptyArgsInstance() {
        return new query_cfg_args();
      }

      public com.xiaomi.infra.pegasus.thrift.async.AsyncMethodCallback<com.xiaomi.infra.pegasus.replication.query_cfg_response> getResultHandler(final com.xiaomi.infra.pegasus.thrift.server.AbstractNonblockingServer.AsyncFrameBuffer fb, final int seqid) {
        final com.xiaomi.infra.pegasus.thrift.AsyncProcessFunction fcall = this;
        return new com.xiaomi.infra.pegasus.thrift.async.AsyncMethodCallback<com.xiaomi.infra.pegasus.replication.query_cfg_response>() { 
          public void onComplete(com.xiaomi.infra.pegasus.replication.query_cfg_response o) {
            query_cfg_result result = new query_cfg_result();
            result.success = o;
            try {
              fcall.sendResponse(fb, result, com.xiaomi.infra.pegasus.thrift.protocol.TMessageType.REPLY,seqid);
            } catch (com.xiaomi.infra.pegasus.thrift.transport.TTransportException e) {
              _LOGGER.error("TTransportException writing to internal frame buffer", e);
              fb.close();
            } catch (java.lang.Exception e) {
              _LOGGER.error("Exception writing to internal frame buffer", e);
              onError(e);
            }
          }
          public void onError(java.lang.Exception e) {
            byte msgType = com.xiaomi.infra.pegasus.thrift.protocol.TMessageType.REPLY;
            com.xiaomi.infra.pegasus.thrift.TSerializable msg;
            query_cfg_result result = new query_cfg_result();
            if (e instanceof com.xiaomi.infra.pegasus.thrift.transport.TTransportException) {
              _LOGGER.error("TTransportException inside handler", e);
              fb.close();
              return;
            } else if (e instanceof com.xiaomi.infra.pegasus.thrift.TApplicationException) {
              _LOGGER.error("TApplicationException inside handler", e);
              msgType = com.xiaomi.infra.pegasus.thrift.protocol.TMessageType.EXCEPTION;
              msg = (com.xiaomi.infra.pegasus.thrift.TApplicationException)e;
            } else {
              _LOGGER.error("Exception inside handler", e);
              msgType = com.xiaomi.infra.pegasus.thrift.protocol.TMessageType.EXCEPTION;
              msg = new com.xiaomi.infra.pegasus.thrift.TApplicationException(com.xiaomi.infra.pegasus.thrift.TApplicationException.INTERNAL_ERROR, e.getMessage());
            }
            try {
              fcall.sendResponse(fb,msg,msgType,seqid);
            } catch (java.lang.Exception ex) {
              _LOGGER.error("Exception writing to internal frame buffer", ex);
              fb.close();
            }
          }
        };
      }

      protected boolean isOneway() {
        return false;
      }

      public void start(I iface, query_cfg_args args, com.xiaomi.infra.pegasus.thrift.async.AsyncMethodCallback<com.xiaomi.infra.pegasus.replication.query_cfg_response> resultHandler) throws com.xiaomi.infra.pegasus.thrift.TException {
        iface.query_cfg(args.query,resultHandler);
      }
    }

  }

  public static class query_cfg_args implements com.xiaomi.infra.pegasus.thrift.TBase<query_cfg_args, query_cfg_args._Fields>, java.io.Serializable, Cloneable, Comparable<query_cfg_args>   {
    private static final com.xiaomi.infra.pegasus.thrift.protocol.TStruct STRUCT_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TStruct("query_cfg_args");

    private static final com.xiaomi.infra.pegasus.thrift.protocol.TField QUERY_FIELD_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TField("query", com.xiaomi.infra.pegasus.thrift.protocol.TType.STRUCT, (short)1);

    private static final com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new query_cfg_argsStandardSchemeFactory();
    private static final com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new query_cfg_argsTupleSchemeFactory();

    public com.xiaomi.infra.pegasus.replication.query_cfg_request query; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements com.xiaomi.infra.pegasus.thrift.TFieldIdEnum {
      QUERY((short)1, "query");

      private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

      static {
        for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // QUERY
            return QUERY;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(java.lang.String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final java.lang.String _fieldName;

      _Fields(short thriftId, java.lang.String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public java.lang.String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final java.util.Map<_Fields, com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      java.util.Map<_Fields, com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.QUERY, new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData("query", com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT, 
          new com.xiaomi.infra.pegasus.thrift.meta_data.StructMetaData(com.xiaomi.infra.pegasus.thrift.protocol.TType.STRUCT, com.xiaomi.infra.pegasus.replication.query_cfg_request.class)));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
      com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData.addStructMetaDataMap(query_cfg_args.class, metaDataMap);
    }

    public query_cfg_args() {
    }

    public query_cfg_args(
      com.xiaomi.infra.pegasus.replication.query_cfg_request query)
    {
      this();
      this.query = query;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public query_cfg_args(query_cfg_args other) {
      if (other.isSetQuery()) {
        this.query = new com.xiaomi.infra.pegasus.replication.query_cfg_request(other.query);
      }
    }

    public query_cfg_args deepCopy() {
      return new query_cfg_args(this);
    }

    @Override
    public void clear() {
      this.query = null;
    }

    public com.xiaomi.infra.pegasus.replication.query_cfg_request getQuery() {
      return this.query;
    }

    public query_cfg_args setQuery(com.xiaomi.infra.pegasus.replication.query_cfg_request query) {
      this.query = query;
      return this;
    }

    public void unsetQuery() {
      this.query = null;
    }

    /** Returns true if field query is set (has been assigned a value) and false otherwise */
    public boolean isSetQuery() {
      return this.query != null;
    }

    public void setQueryIsSet(boolean value) {
      if (!value) {
        this.query = null;
      }
    }

    public void setFieldValue(_Fields field, java.lang.Object value) {
      switch (field) {
      case QUERY:
        if (value == null) {
          unsetQuery();
        } else {
          setQuery((com.xiaomi.infra.pegasus.replication.query_cfg_request)value);
        }
        break;

      }
    }

    public java.lang.Object getFieldValue(_Fields field) {
      switch (field) {
      case QUERY:
        return getQuery();

      }
      throw new java.lang.IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new java.lang.IllegalArgumentException();
      }

      switch (field) {
      case QUERY:
        return isSetQuery();
      }
      throw new java.lang.IllegalStateException();
    }

    @Override
    public boolean equals(java.lang.Object that) {
      if (that == null)
        return false;
      if (that instanceof query_cfg_args)
        return this.equals((query_cfg_args)that);
      return false;
    }

    public boolean equals(query_cfg_args that) {
      if (that == null)
        return false;
      if (this == that)
        return true;

      boolean this_present_query = true && this.isSetQuery();
      boolean that_present_query = true && that.isSetQuery();
      if (this_present_query || that_present_query) {
        if (!(this_present_query && that_present_query))
          return false;
        if (!this.query.equals(that.query))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int hashCode = 1;

      hashCode = hashCode * 8191 + ((isSetQuery()) ? 131071 : 524287);
      if (isSetQuery())
        hashCode = hashCode * 8191 + query.hashCode();

      return hashCode;
    }

    @Override
    public int compareTo(query_cfg_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = java.lang.Boolean.valueOf(isSetQuery()).compareTo(other.isSetQuery());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetQuery()) {
        lastComparison = com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(this.query, other.query);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol iprot) throws com.xiaomi.infra.pegasus.thrift.TException {
      scheme(iprot).read(iprot, this);
    }

    public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol oprot) throws com.xiaomi.infra.pegasus.thrift.TException {
      scheme(oprot).write(oprot, this);
    }

    @Override
    public java.lang.String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder("query_cfg_args(");
      boolean first = true;

      sb.append("query:");
      if (this.query == null) {
        sb.append("null");
      } else {
        sb.append(this.query);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws com.xiaomi.infra.pegasus.thrift.TException {
      // check for required fields
      // check for sub-struct validity
      if (query != null) {
        query.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new com.xiaomi.infra.pegasus.thrift.protocol.TCompactProtocol(new com.xiaomi.infra.pegasus.thrift.transport.TIOStreamTransport(out)));
      } catch (com.xiaomi.infra.pegasus.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
      try {
        read(new com.xiaomi.infra.pegasus.thrift.protocol.TCompactProtocol(new com.xiaomi.infra.pegasus.thrift.transport.TIOStreamTransport(in)));
      } catch (com.xiaomi.infra.pegasus.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class query_cfg_argsStandardSchemeFactory implements com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory {
      public query_cfg_argsStandardScheme getScheme() {
        return new query_cfg_argsStandardScheme();
      }
    }

    private static class query_cfg_argsStandardScheme extends com.xiaomi.infra.pegasus.thrift.scheme.StandardScheme<query_cfg_args> {

      public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol iprot, query_cfg_args struct) throws com.xiaomi.infra.pegasus.thrift.TException {
        com.xiaomi.infra.pegasus.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // QUERY
              if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.STRUCT) {
                struct.query = new com.xiaomi.infra.pegasus.replication.query_cfg_request();
                struct.query.read(iprot);
                struct.setQueryIsSet(true);
              } else { 
                com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol oprot, query_cfg_args struct) throws com.xiaomi.infra.pegasus.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.query != null) {
          oprot.writeFieldBegin(QUERY_FIELD_DESC);
          struct.query.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class query_cfg_argsTupleSchemeFactory implements com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory {
      public query_cfg_argsTupleScheme getScheme() {
        return new query_cfg_argsTupleScheme();
      }
    }

    private static class query_cfg_argsTupleScheme extends com.xiaomi.infra.pegasus.thrift.scheme.TupleScheme<query_cfg_args> {

      @Override
      public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot, query_cfg_args struct) throws com.xiaomi.infra.pegasus.thrift.TException {
        com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol oprot = (com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet optionals = new java.util.BitSet();
        if (struct.isSetQuery()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetQuery()) {
          struct.query.write(oprot);
        }
      }

      @Override
      public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot, query_cfg_args struct) throws com.xiaomi.infra.pegasus.thrift.TException {
        com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol iprot = (com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.query = new com.xiaomi.infra.pegasus.replication.query_cfg_request();
          struct.query.read(iprot);
          struct.setQueryIsSet(true);
        }
      }
    }

    private static <S extends com.xiaomi.infra.pegasus.thrift.scheme.IScheme> S scheme(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol proto) {
      return (com.xiaomi.infra.pegasus.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
    }
  }

  public static class query_cfg_result implements com.xiaomi.infra.pegasus.thrift.TBase<query_cfg_result, query_cfg_result._Fields>, java.io.Serializable, Cloneable, Comparable<query_cfg_result>   {
    private static final com.xiaomi.infra.pegasus.thrift.protocol.TStruct STRUCT_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TStruct("query_cfg_result");

    private static final com.xiaomi.infra.pegasus.thrift.protocol.TField SUCCESS_FIELD_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TField("success", com.xiaomi.infra.pegasus.thrift.protocol.TType.STRUCT, (short)0);

    private static final com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new query_cfg_resultStandardSchemeFactory();
    private static final com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new query_cfg_resultTupleSchemeFactory();

    public com.xiaomi.infra.pegasus.replication.query_cfg_response success; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements com.xiaomi.infra.pegasus.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success");

      private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

      static {
        for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(java.lang.String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final java.lang.String _fieldName;

      _Fields(short thriftId, java.lang.String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public java.lang.String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final java.util.Map<_Fields, com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      java.util.Map<_Fields, com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData("success", com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT, 
          new com.xiaomi.infra.pegasus.thrift.meta_data.StructMetaData(com.xiaomi.infra.pegasus.thrift.protocol.TType.STRUCT, com.xiaomi.infra.pegasus.replication.query_cfg_response.class)));
      metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
      com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData.addStructMetaDataMap(query_cfg_result.class, metaDataMap);
    }

    public query_cfg_result() {
    }

    public query_cfg_result(
      com.xiaomi.infra.pegasus.replication.query_cfg_response success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public query_cfg_result(query_cfg_result other) {
      if (other.isSetSuccess()) {
        this.success = new com.xiaomi.infra.pegasus.replication.query_cfg_response(other.success);
      }
    }

    public query_cfg_result deepCopy() {
      return new query_cfg_result(this);
    }

    @Override
    public void clear() {
      this.success = null;
    }

    public com.xiaomi.infra.pegasus.replication.query_cfg_response getSuccess() {
      return this.success;
    }

    public query_cfg_result setSuccess(com.xiaomi.infra.pegasus.replication.query_cfg_response success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public void setFieldValue(_Fields field, java.lang.Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((com.xiaomi.infra.pegasus.replication.query_cfg_response)value);
        }
        break;

      }
    }

    public java.lang.Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      }
      throw new java.lang.IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new java.lang.IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      }
      throw new java.lang.IllegalStateException();
    }

    @Override
    public boolean equals(java.lang.Object that) {
      if (that == null)
        return false;
      if (that instanceof query_cfg_result)
        return this.equals((query_cfg_result)that);
      return false;
    }

    public boolean equals(query_cfg_result that) {
      if (that == null)
        return false;
      if (this == that)
        return true;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int hashCode = 1;

      hashCode = hashCode * 8191 + ((isSetSuccess()) ? 131071 : 524287);
      if (isSetSuccess())
        hashCode = hashCode * 8191 + success.hashCode();

      return hashCode;
    }

    @Override
    public int compareTo(query_cfg_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;

      lastComparison = java.lang.Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(this.success, other.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol iprot) throws com.xiaomi.infra.pegasus.thrift.TException {
      scheme(iprot).read(iprot, this);
    }

    public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol oprot) throws com.xiaomi.infra.pegasus.thrift.TException {
      scheme(oprot).write(oprot, this);
      }

    @Override
    public java.lang.String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder("query_cfg_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws com.xiaomi.infra.pegasus.thrift.TException {
      // check for required fields
      // check for sub-struct validity
      if (success != null) {
        success.validate();
      }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new com.xiaomi.infra.pegasus.thrift.protocol.TCompactProtocol(new com.xiaomi.infra.pegasus.thrift.transport.TIOStreamTransport(out)));
      } catch (com.xiaomi.infra.pegasus.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
      try {
        read(new com.xiaomi.infra.pegasus.thrift.protocol.TCompactProtocol(new com.xiaomi.infra.pegasus.thrift.transport.TIOStreamTransport(in)));
      } catch (com.xiaomi.infra.pegasus.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class query_cfg_resultStandardSchemeFactory implements com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory {
      public query_cfg_resultStandardScheme getScheme() {
        return new query_cfg_resultStandardScheme();
      }
    }

    private static class query_cfg_resultStandardScheme extends com.xiaomi.infra.pegasus.thrift.scheme.StandardScheme<query_cfg_result> {

      public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol iprot, query_cfg_result struct) throws com.xiaomi.infra.pegasus.thrift.TException {
        com.xiaomi.infra.pegasus.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.STRUCT) {
                struct.success = new com.xiaomi.infra.pegasus.replication.query_cfg_response();
                struct.success.read(iprot);
                struct.setSuccessIsSet(true);
              } else { 
                com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol oprot, query_cfg_result struct) throws com.xiaomi.infra.pegasus.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          struct.success.write(oprot);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class query_cfg_resultTupleSchemeFactory implements com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory {
      public query_cfg_resultTupleScheme getScheme() {
        return new query_cfg_resultTupleScheme();
      }
    }

    private static class query_cfg_resultTupleScheme extends com.xiaomi.infra.pegasus.thrift.scheme.TupleScheme<query_cfg_result> {

      @Override
      public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot, query_cfg_result struct) throws com.xiaomi.infra.pegasus.thrift.TException {
        com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol oprot = (com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet optionals = new java.util.BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetSuccess()) {
          struct.success.write(oprot);
        }
      }

      @Override
      public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot, query_cfg_result struct) throws com.xiaomi.infra.pegasus.thrift.TException {
        com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol iprot = (com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol) prot;
        java.util.BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.success = new com.xiaomi.infra.pegasus.replication.query_cfg_response();
          struct.success.read(iprot);
          struct.setSuccessIsSet(true);
        }
      }
    }

    private static <S extends com.xiaomi.infra.pegasus.thrift.scheme.IScheme> S scheme(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol proto) {
      return (com.xiaomi.infra.pegasus.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
    }
  }

}

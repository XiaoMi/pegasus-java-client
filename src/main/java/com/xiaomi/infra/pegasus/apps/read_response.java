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
public class read_response implements com.xiaomi.infra.pegasus.thrift.TBase<read_response, read_response._Fields>, java.io.Serializable, Cloneable, Comparable<read_response> {
  private static final com.xiaomi.infra.pegasus.thrift.protocol.TStruct STRUCT_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TStruct("read_response");

  private static final com.xiaomi.infra.pegasus.thrift.protocol.TField ERROR_FIELD_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TField("error", com.xiaomi.infra.pegasus.thrift.protocol.TType.I32, (short)1);
  private static final com.xiaomi.infra.pegasus.thrift.protocol.TField VALUE_FIELD_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TField("value", com.xiaomi.infra.pegasus.thrift.protocol.TType.STRUCT, (short)2);
  private static final com.xiaomi.infra.pegasus.thrift.protocol.TField APP_ID_FIELD_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TField("app_id", com.xiaomi.infra.pegasus.thrift.protocol.TType.I32, (short)3);
  private static final com.xiaomi.infra.pegasus.thrift.protocol.TField PARTITION_INDEX_FIELD_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TField("partition_index", com.xiaomi.infra.pegasus.thrift.protocol.TType.I32, (short)4);
  private static final com.xiaomi.infra.pegasus.thrift.protocol.TField SERVER_FIELD_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TField("server", com.xiaomi.infra.pegasus.thrift.protocol.TType.STRING, (short)6);

  private static final com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new read_responseStandardSchemeFactory();
  private static final com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new read_responseTupleSchemeFactory();

  public int error; // required
  public com.xiaomi.infra.pegasus.base.blob value; // required
  public int app_id; // required
  public int partition_index; // required
  public java.lang.String server; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements com.xiaomi.infra.pegasus.thrift.TFieldIdEnum {
    ERROR((short)1, "error"),
    VALUE((short)2, "value"),
    APP_ID((short)3, "app_id"),
    PARTITION_INDEX((short)4, "partition_index"),
    SERVER((short)6, "server");

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
        case 1: // ERROR
          return ERROR;
        case 2: // VALUE
          return VALUE;
        case 3: // APP_ID
          return APP_ID;
        case 4: // PARTITION_INDEX
          return PARTITION_INDEX;
        case 6: // SERVER
          return SERVER;
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
  private static final int __ERROR_ISSET_ID = 0;
  private static final int __APP_ID_ISSET_ID = 1;
  private static final int __PARTITION_INDEX_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ERROR, new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData("error", com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT, 
        new com.xiaomi.infra.pegasus.thrift.meta_data.FieldValueMetaData(com.xiaomi.infra.pegasus.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.VALUE, new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData("value", com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT, 
        new com.xiaomi.infra.pegasus.thrift.meta_data.StructMetaData(com.xiaomi.infra.pegasus.thrift.protocol.TType.STRUCT, com.xiaomi.infra.pegasus.base.blob.class)));
    tmpMap.put(_Fields.APP_ID, new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData("app_id", com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT, 
        new com.xiaomi.infra.pegasus.thrift.meta_data.FieldValueMetaData(com.xiaomi.infra.pegasus.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PARTITION_INDEX, new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData("partition_index", com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT, 
        new com.xiaomi.infra.pegasus.thrift.meta_data.FieldValueMetaData(com.xiaomi.infra.pegasus.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SERVER, new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData("server", com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT, 
        new com.xiaomi.infra.pegasus.thrift.meta_data.FieldValueMetaData(com.xiaomi.infra.pegasus.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData.addStructMetaDataMap(read_response.class, metaDataMap);
  }

  public read_response() {
  }

  public read_response(
    int error,
    com.xiaomi.infra.pegasus.base.blob value,
    int app_id,
    int partition_index,
    java.lang.String server)
  {
    this();
    this.error = error;
    setErrorIsSet(true);
    this.value = value;
    this.app_id = app_id;
    setApp_idIsSet(true);
    this.partition_index = partition_index;
    setPartition_indexIsSet(true);
    this.server = server;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public read_response(read_response other) {
    __isset_bitfield = other.__isset_bitfield;
    this.error = other.error;
    if (other.isSetValue()) {
      this.value = new com.xiaomi.infra.pegasus.base.blob(other.value);
    }
    this.app_id = other.app_id;
    this.partition_index = other.partition_index;
    if (other.isSetServer()) {
      this.server = other.server;
    }
  }

  public read_response deepCopy() {
    return new read_response(this);
  }

  @Override
  public void clear() {
    setErrorIsSet(false);
    this.error = 0;
    this.value = null;
    setApp_idIsSet(false);
    this.app_id = 0;
    setPartition_indexIsSet(false);
    this.partition_index = 0;
    this.server = null;
  }

  public int getError() {
    return this.error;
  }

  public read_response setError(int error) {
    this.error = error;
    setErrorIsSet(true);
    return this;
  }

  public void unsetError() {
    __isset_bitfield = com.xiaomi.infra.pegasus.thrift.EncodingUtils.clearBit(__isset_bitfield, __ERROR_ISSET_ID);
  }

  /** Returns true if field error is set (has been assigned a value) and false otherwise */
  public boolean isSetError() {
    return com.xiaomi.infra.pegasus.thrift.EncodingUtils.testBit(__isset_bitfield, __ERROR_ISSET_ID);
  }

  public void setErrorIsSet(boolean value) {
    __isset_bitfield = com.xiaomi.infra.pegasus.thrift.EncodingUtils.setBit(__isset_bitfield, __ERROR_ISSET_ID, value);
  }

  public com.xiaomi.infra.pegasus.base.blob getValue() {
    return this.value;
  }

  public read_response setValue(com.xiaomi.infra.pegasus.base.blob value) {
    this.value = value;
    return this;
  }

  public void unsetValue() {
    this.value = null;
  }

  /** Returns true if field value is set (has been assigned a value) and false otherwise */
  public boolean isSetValue() {
    return this.value != null;
  }

  public void setValueIsSet(boolean value) {
    if (!value) {
      this.value = null;
    }
  }

  public int getApp_id() {
    return this.app_id;
  }

  public read_response setApp_id(int app_id) {
    this.app_id = app_id;
    setApp_idIsSet(true);
    return this;
  }

  public void unsetApp_id() {
    __isset_bitfield = com.xiaomi.infra.pegasus.thrift.EncodingUtils.clearBit(__isset_bitfield, __APP_ID_ISSET_ID);
  }

  /** Returns true if field app_id is set (has been assigned a value) and false otherwise */
  public boolean isSetApp_id() {
    return com.xiaomi.infra.pegasus.thrift.EncodingUtils.testBit(__isset_bitfield, __APP_ID_ISSET_ID);
  }

  public void setApp_idIsSet(boolean value) {
    __isset_bitfield = com.xiaomi.infra.pegasus.thrift.EncodingUtils.setBit(__isset_bitfield, __APP_ID_ISSET_ID, value);
  }

  public int getPartition_index() {
    return this.partition_index;
  }

  public read_response setPartition_index(int partition_index) {
    this.partition_index = partition_index;
    setPartition_indexIsSet(true);
    return this;
  }

  public void unsetPartition_index() {
    __isset_bitfield = com.xiaomi.infra.pegasus.thrift.EncodingUtils.clearBit(__isset_bitfield, __PARTITION_INDEX_ISSET_ID);
  }

  /** Returns true if field partition_index is set (has been assigned a value) and false otherwise */
  public boolean isSetPartition_index() {
    return com.xiaomi.infra.pegasus.thrift.EncodingUtils.testBit(__isset_bitfield, __PARTITION_INDEX_ISSET_ID);
  }

  public void setPartition_indexIsSet(boolean value) {
    __isset_bitfield = com.xiaomi.infra.pegasus.thrift.EncodingUtils.setBit(__isset_bitfield, __PARTITION_INDEX_ISSET_ID, value);
  }

  public java.lang.String getServer() {
    return this.server;
  }

  public read_response setServer(java.lang.String server) {
    this.server = server;
    return this;
  }

  public void unsetServer() {
    this.server = null;
  }

  /** Returns true if field server is set (has been assigned a value) and false otherwise */
  public boolean isSetServer() {
    return this.server != null;
  }

  public void setServerIsSet(boolean value) {
    if (!value) {
      this.server = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case ERROR:
      if (value == null) {
        unsetError();
      } else {
        setError((java.lang.Integer)value);
      }
      break;

    case VALUE:
      if (value == null) {
        unsetValue();
      } else {
        setValue((com.xiaomi.infra.pegasus.base.blob)value);
      }
      break;

    case APP_ID:
      if (value == null) {
        unsetApp_id();
      } else {
        setApp_id((java.lang.Integer)value);
      }
      break;

    case PARTITION_INDEX:
      if (value == null) {
        unsetPartition_index();
      } else {
        setPartition_index((java.lang.Integer)value);
      }
      break;

    case SERVER:
      if (value == null) {
        unsetServer();
      } else {
        setServer((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ERROR:
      return getError();

    case VALUE:
      return getValue();

    case APP_ID:
      return getApp_id();

    case PARTITION_INDEX:
      return getPartition_index();

    case SERVER:
      return getServer();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ERROR:
      return isSetError();
    case VALUE:
      return isSetValue();
    case APP_ID:
      return isSetApp_id();
    case PARTITION_INDEX:
      return isSetPartition_index();
    case SERVER:
      return isSetServer();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof read_response)
      return this.equals((read_response)that);
    return false;
  }

  public boolean equals(read_response that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_error = true;
    boolean that_present_error = true;
    if (this_present_error || that_present_error) {
      if (!(this_present_error && that_present_error))
        return false;
      if (this.error != that.error)
        return false;
    }

    boolean this_present_value = true && this.isSetValue();
    boolean that_present_value = true && that.isSetValue();
    if (this_present_value || that_present_value) {
      if (!(this_present_value && that_present_value))
        return false;
      if (!this.value.equals(that.value))
        return false;
    }

    boolean this_present_app_id = true;
    boolean that_present_app_id = true;
    if (this_present_app_id || that_present_app_id) {
      if (!(this_present_app_id && that_present_app_id))
        return false;
      if (this.app_id != that.app_id)
        return false;
    }

    boolean this_present_partition_index = true;
    boolean that_present_partition_index = true;
    if (this_present_partition_index || that_present_partition_index) {
      if (!(this_present_partition_index && that_present_partition_index))
        return false;
      if (this.partition_index != that.partition_index)
        return false;
    }

    boolean this_present_server = true && this.isSetServer();
    boolean that_present_server = true && that.isSetServer();
    if (this_present_server || that_present_server) {
      if (!(this_present_server && that_present_server))
        return false;
      if (!this.server.equals(that.server))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + error;

    hashCode = hashCode * 8191 + ((isSetValue()) ? 131071 : 524287);
    if (isSetValue())
      hashCode = hashCode * 8191 + value.hashCode();

    hashCode = hashCode * 8191 + app_id;

    hashCode = hashCode * 8191 + partition_index;

    hashCode = hashCode * 8191 + ((isSetServer()) ? 131071 : 524287);
    if (isSetServer())
      hashCode = hashCode * 8191 + server.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(read_response other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetError()).compareTo(other.isSetError());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetError()) {
      lastComparison = com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(this.error, other.error);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetValue()).compareTo(other.isSetValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetValue()) {
      lastComparison = com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(this.value, other.value);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetApp_id()).compareTo(other.isSetApp_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetApp_id()) {
      lastComparison = com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(this.app_id, other.app_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPartition_index()).compareTo(other.isSetPartition_index());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartition_index()) {
      lastComparison = com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(this.partition_index, other.partition_index);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetServer()).compareTo(other.isSetServer());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetServer()) {
      lastComparison = com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(this.server, other.server);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("read_response(");
    boolean first = true;

    sb.append("error:");
    sb.append(this.error);
    first = false;
    if (!first) sb.append(", ");
    sb.append("value:");
    if (this.value == null) {
      sb.append("null");
    } else {
      sb.append(this.value);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("app_id:");
    sb.append(this.app_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("partition_index:");
    sb.append(this.partition_index);
    first = false;
    if (!first) sb.append(", ");
    sb.append("server:");
    if (this.server == null) {
      sb.append("null");
    } else {
      sb.append(this.server);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws com.xiaomi.infra.pegasus.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (value != null) {
      value.validate();
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new com.xiaomi.infra.pegasus.thrift.protocol.TCompactProtocol(new com.xiaomi.infra.pegasus.thrift.transport.TIOStreamTransport(in)));
    } catch (com.xiaomi.infra.pegasus.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class read_responseStandardSchemeFactory implements com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory {
    public read_responseStandardScheme getScheme() {
      return new read_responseStandardScheme();
    }
  }

  private static class read_responseStandardScheme extends com.xiaomi.infra.pegasus.thrift.scheme.StandardScheme<read_response> {

    public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol iprot, read_response struct) throws com.xiaomi.infra.pegasus.thrift.TException {
      com.xiaomi.infra.pegasus.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ERROR
            if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.I32) {
              struct.error = iprot.readI32();
              struct.setErrorIsSet(true);
            } else { 
              com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // VALUE
            if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.STRUCT) {
              struct.value = new com.xiaomi.infra.pegasus.base.blob();
              struct.value.read(iprot);
              struct.setValueIsSet(true);
            } else { 
              com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // APP_ID
            if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.I32) {
              struct.app_id = iprot.readI32();
              struct.setApp_idIsSet(true);
            } else { 
              com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PARTITION_INDEX
            if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.I32) {
              struct.partition_index = iprot.readI32();
              struct.setPartition_indexIsSet(true);
            } else { 
              com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // SERVER
            if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.STRING) {
              struct.server = iprot.readString();
              struct.setServerIsSet(true);
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

    public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol oprot, read_response struct) throws com.xiaomi.infra.pegasus.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ERROR_FIELD_DESC);
      oprot.writeI32(struct.error);
      oprot.writeFieldEnd();
      if (struct.value != null) {
        oprot.writeFieldBegin(VALUE_FIELD_DESC);
        struct.value.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(APP_ID_FIELD_DESC);
      oprot.writeI32(struct.app_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PARTITION_INDEX_FIELD_DESC);
      oprot.writeI32(struct.partition_index);
      oprot.writeFieldEnd();
      if (struct.server != null) {
        oprot.writeFieldBegin(SERVER_FIELD_DESC);
        oprot.writeString(struct.server);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class read_responseTupleSchemeFactory implements com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory {
    public read_responseTupleScheme getScheme() {
      return new read_responseTupleScheme();
    }
  }

  private static class read_responseTupleScheme extends com.xiaomi.infra.pegasus.thrift.scheme.TupleScheme<read_response> {

    @Override
    public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot, read_response struct) throws com.xiaomi.infra.pegasus.thrift.TException {
      com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol oprot = (com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetError()) {
        optionals.set(0);
      }
      if (struct.isSetValue()) {
        optionals.set(1);
      }
      if (struct.isSetApp_id()) {
        optionals.set(2);
      }
      if (struct.isSetPartition_index()) {
        optionals.set(3);
      }
      if (struct.isSetServer()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetError()) {
        oprot.writeI32(struct.error);
      }
      if (struct.isSetValue()) {
        struct.value.write(oprot);
      }
      if (struct.isSetApp_id()) {
        oprot.writeI32(struct.app_id);
      }
      if (struct.isSetPartition_index()) {
        oprot.writeI32(struct.partition_index);
      }
      if (struct.isSetServer()) {
        oprot.writeString(struct.server);
      }
    }

    @Override
    public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot, read_response struct) throws com.xiaomi.infra.pegasus.thrift.TException {
      com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol iprot = (com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.error = iprot.readI32();
        struct.setErrorIsSet(true);
      }
      if (incoming.get(1)) {
        struct.value = new com.xiaomi.infra.pegasus.base.blob();
        struct.value.read(iprot);
        struct.setValueIsSet(true);
      }
      if (incoming.get(2)) {
        struct.app_id = iprot.readI32();
        struct.setApp_idIsSet(true);
      }
      if (incoming.get(3)) {
        struct.partition_index = iprot.readI32();
        struct.setPartition_indexIsSet(true);
      }
      if (incoming.get(4)) {
        struct.server = iprot.readString();
        struct.setServerIsSet(true);
      }
    }
  }

  private static <S extends com.xiaomi.infra.pegasus.thrift.scheme.IScheme> S scheme(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol proto) {
    return (com.xiaomi.infra.pegasus.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}


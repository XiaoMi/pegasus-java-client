// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.xiaomi.infra.pegasus.replication;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2018-09-11")
public class query_cfg_request implements com.xiaomi.infra.pegasus.thrift.TBase<query_cfg_request, query_cfg_request._Fields>, java.io.Serializable, Cloneable, Comparable<query_cfg_request> {
  private static final com.xiaomi.infra.pegasus.thrift.protocol.TStruct STRUCT_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TStruct("query_cfg_request");

  private static final com.xiaomi.infra.pegasus.thrift.protocol.TField APP_NAME_FIELD_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TField("app_name", com.xiaomi.infra.pegasus.thrift.protocol.TType.STRING, (short)1);
  private static final com.xiaomi.infra.pegasus.thrift.protocol.TField PARTITION_INDICES_FIELD_DESC = new com.xiaomi.infra.pegasus.thrift.protocol.TField("partition_indices", com.xiaomi.infra.pegasus.thrift.protocol.TType.LIST, (short)2);

  private static final com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new query_cfg_requestStandardSchemeFactory();
  private static final com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new query_cfg_requestTupleSchemeFactory();

  public java.lang.String app_name; // required
  public java.util.List<java.lang.Integer> partition_indices; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements com.xiaomi.infra.pegasus.thrift.TFieldIdEnum {
    APP_NAME((short)1, "app_name"),
    PARTITION_INDICES((short)2, "partition_indices");

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
        case 1: // APP_NAME
          return APP_NAME;
        case 2: // PARTITION_INDICES
          return PARTITION_INDICES;
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
    tmpMap.put(_Fields.APP_NAME, new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData("app_name", com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT, 
        new com.xiaomi.infra.pegasus.thrift.meta_data.FieldValueMetaData(com.xiaomi.infra.pegasus.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PARTITION_INDICES, new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData("partition_indices", com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT, 
        new com.xiaomi.infra.pegasus.thrift.meta_data.ListMetaData(com.xiaomi.infra.pegasus.thrift.protocol.TType.LIST, 
            new com.xiaomi.infra.pegasus.thrift.meta_data.FieldValueMetaData(com.xiaomi.infra.pegasus.thrift.protocol.TType.I32))));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData.addStructMetaDataMap(query_cfg_request.class, metaDataMap);
  }

  public query_cfg_request() {
  }

  public query_cfg_request(
    java.lang.String app_name,
    java.util.List<java.lang.Integer> partition_indices)
  {
    this();
    this.app_name = app_name;
    this.partition_indices = partition_indices;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public query_cfg_request(query_cfg_request other) {
    if (other.isSetApp_name()) {
      this.app_name = other.app_name;
    }
    if (other.isSetPartition_indices()) {
      java.util.List<java.lang.Integer> __this__partition_indices = new java.util.ArrayList<java.lang.Integer>(other.partition_indices);
      this.partition_indices = __this__partition_indices;
    }
  }

  public query_cfg_request deepCopy() {
    return new query_cfg_request(this);
  }

  @Override
  public void clear() {
    this.app_name = null;
    this.partition_indices = null;
  }

  public java.lang.String getApp_name() {
    return this.app_name;
  }

  public query_cfg_request setApp_name(java.lang.String app_name) {
    this.app_name = app_name;
    return this;
  }

  public void unsetApp_name() {
    this.app_name = null;
  }

  /** Returns true if field app_name is set (has been assigned a value) and false otherwise */
  public boolean isSetApp_name() {
    return this.app_name != null;
  }

  public void setApp_nameIsSet(boolean value) {
    if (!value) {
      this.app_name = null;
    }
  }

  public int getPartition_indicesSize() {
    return (this.partition_indices == null) ? 0 : this.partition_indices.size();
  }

  public java.util.Iterator<java.lang.Integer> getPartition_indicesIterator() {
    return (this.partition_indices == null) ? null : this.partition_indices.iterator();
  }

  public void addToPartition_indices(int elem) {
    if (this.partition_indices == null) {
      this.partition_indices = new java.util.ArrayList<java.lang.Integer>();
    }
    this.partition_indices.add(elem);
  }

  public java.util.List<java.lang.Integer> getPartition_indices() {
    return this.partition_indices;
  }

  public query_cfg_request setPartition_indices(java.util.List<java.lang.Integer> partition_indices) {
    this.partition_indices = partition_indices;
    return this;
  }

  public void unsetPartition_indices() {
    this.partition_indices = null;
  }

  /** Returns true if field partition_indices is set (has been assigned a value) and false otherwise */
  public boolean isSetPartition_indices() {
    return this.partition_indices != null;
  }

  public void setPartition_indicesIsSet(boolean value) {
    if (!value) {
      this.partition_indices = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case APP_NAME:
      if (value == null) {
        unsetApp_name();
      } else {
        setApp_name((java.lang.String)value);
      }
      break;

    case PARTITION_INDICES:
      if (value == null) {
        unsetPartition_indices();
      } else {
        setPartition_indices((java.util.List<java.lang.Integer>)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case APP_NAME:
      return getApp_name();

    case PARTITION_INDICES:
      return getPartition_indices();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case APP_NAME:
      return isSetApp_name();
    case PARTITION_INDICES:
      return isSetPartition_indices();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof query_cfg_request)
      return this.equals((query_cfg_request)that);
    return false;
  }

  public boolean equals(query_cfg_request that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_app_name = true && this.isSetApp_name();
    boolean that_present_app_name = true && that.isSetApp_name();
    if (this_present_app_name || that_present_app_name) {
      if (!(this_present_app_name && that_present_app_name))
        return false;
      if (!this.app_name.equals(that.app_name))
        return false;
    }

    boolean this_present_partition_indices = true && this.isSetPartition_indices();
    boolean that_present_partition_indices = true && that.isSetPartition_indices();
    if (this_present_partition_indices || that_present_partition_indices) {
      if (!(this_present_partition_indices && that_present_partition_indices))
        return false;
      if (!this.partition_indices.equals(that.partition_indices))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetApp_name()) ? 131071 : 524287);
    if (isSetApp_name())
      hashCode = hashCode * 8191 + app_name.hashCode();

    hashCode = hashCode * 8191 + ((isSetPartition_indices()) ? 131071 : 524287);
    if (isSetPartition_indices())
      hashCode = hashCode * 8191 + partition_indices.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(query_cfg_request other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetApp_name()).compareTo(other.isSetApp_name());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetApp_name()) {
      lastComparison = com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(this.app_name, other.app_name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPartition_indices()).compareTo(other.isSetPartition_indices());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartition_indices()) {
      lastComparison = com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(this.partition_indices, other.partition_indices);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("query_cfg_request(");
    boolean first = true;

    sb.append("app_name:");
    if (this.app_name == null) {
      sb.append("null");
    } else {
      sb.append(this.app_name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("partition_indices:");
    if (this.partition_indices == null) {
      sb.append("null");
    } else {
      sb.append(this.partition_indices);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws com.xiaomi.infra.pegasus.thrift.TException {
    // check for required fields
    // check for sub-struct validity
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

  private static class query_cfg_requestStandardSchemeFactory implements com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory {
    public query_cfg_requestStandardScheme getScheme() {
      return new query_cfg_requestStandardScheme();
    }
  }

  private static class query_cfg_requestStandardScheme extends com.xiaomi.infra.pegasus.thrift.scheme.StandardScheme<query_cfg_request> {

    public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol iprot, query_cfg_request struct) throws com.xiaomi.infra.pegasus.thrift.TException {
      com.xiaomi.infra.pegasus.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // APP_NAME
            if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.STRING) {
              struct.app_name = iprot.readString();
              struct.setApp_nameIsSet(true);
            } else { 
              com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PARTITION_INDICES
            if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.LIST) {
              {
                com.xiaomi.infra.pegasus.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.partition_indices = new java.util.ArrayList<java.lang.Integer>(_list16.size);
                int _elem17;
                for (int _i18 = 0; _i18 < _list16.size; ++_i18)
                {
                  _elem17 = iprot.readI32();
                  struct.partition_indices.add(_elem17);
                }
                iprot.readListEnd();
              }
              struct.setPartition_indicesIsSet(true);
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

    public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol oprot, query_cfg_request struct) throws com.xiaomi.infra.pegasus.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.app_name != null) {
        oprot.writeFieldBegin(APP_NAME_FIELD_DESC);
        oprot.writeString(struct.app_name);
        oprot.writeFieldEnd();
      }
      if (struct.partition_indices != null) {
        oprot.writeFieldBegin(PARTITION_INDICES_FIELD_DESC);
        {
          oprot.writeListBegin(new com.xiaomi.infra.pegasus.thrift.protocol.TList(com.xiaomi.infra.pegasus.thrift.protocol.TType.I32, struct.partition_indices.size()));
          for (int _iter19 : struct.partition_indices)
          {
            oprot.writeI32(_iter19);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class query_cfg_requestTupleSchemeFactory implements com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory {
    public query_cfg_requestTupleScheme getScheme() {
      return new query_cfg_requestTupleScheme();
    }
  }

  private static class query_cfg_requestTupleScheme extends com.xiaomi.infra.pegasus.thrift.scheme.TupleScheme<query_cfg_request> {

    @Override
    public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot, query_cfg_request struct) throws com.xiaomi.infra.pegasus.thrift.TException {
      com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol oprot = (com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetApp_name()) {
        optionals.set(0);
      }
      if (struct.isSetPartition_indices()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetApp_name()) {
        oprot.writeString(struct.app_name);
      }
      if (struct.isSetPartition_indices()) {
        {
          oprot.writeI32(struct.partition_indices.size());
          for (int _iter20 : struct.partition_indices)
          {
            oprot.writeI32(_iter20);
          }
        }
      }
    }

    @Override
    public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot, query_cfg_request struct) throws com.xiaomi.infra.pegasus.thrift.TException {
      com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol iprot = (com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.app_name = iprot.readString();
        struct.setApp_nameIsSet(true);
      }
      if (incoming.get(1)) {
        {
          com.xiaomi.infra.pegasus.thrift.protocol.TList _list21 = new com.xiaomi.infra.pegasus.thrift.protocol.TList(com.xiaomi.infra.pegasus.thrift.protocol.TType.I32, iprot.readI32());
          struct.partition_indices = new java.util.ArrayList<java.lang.Integer>(_list21.size);
          int _elem22;
          for (int _i23 = 0; _i23 < _list21.size; ++_i23)
          {
            _elem22 = iprot.readI32();
            struct.partition_indices.add(_elem22);
          }
        }
        struct.setPartition_indicesIsSet(true);
      }
    }
  }

  private static <S extends com.xiaomi.infra.pegasus.thrift.scheme.IScheme> S scheme(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol proto) {
    return (com.xiaomi.infra.pegasus.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}


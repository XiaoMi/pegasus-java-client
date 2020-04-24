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
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2020-04-13")
public class scan_request implements org.apache.thrift.TBase<scan_request, scan_request._Fields>, java.io.Serializable, Cloneable, Comparable<scan_request> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("scan_request");

  private static final org.apache.thrift.protocol.TField CONTEXT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("context_id", org.apache.thrift.protocol.TType.I64, (short)1);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new scan_requestStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new scan_requestTupleSchemeFactory();

  public long context_id; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CONTEXT_ID((short)1, "context_id");

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
        case 1: // CONTEXT_ID
          return CONTEXT_ID;
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
  private static final int __CONTEXT_ID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CONTEXT_ID, new org.apache.thrift.meta_data.FieldMetaData("context_id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(scan_request.class, metaDataMap);
  }

  public scan_request() {
  }

  public scan_request(
    long context_id)
  {
    this();
    this.context_id = context_id;
    setContext_idIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public scan_request(scan_request other) {
    __isset_bitfield = other.__isset_bitfield;
    this.context_id = other.context_id;
  }

  public scan_request deepCopy() {
    return new scan_request(this);
  }

  @Override
  public void clear() {
    setContext_idIsSet(false);
    this.context_id = 0;
  }

  public long getContext_id() {
    return this.context_id;
  }

  public scan_request setContext_id(long context_id) {
    this.context_id = context_id;
    setContext_idIsSet(true);
    return this;
  }

  public void unsetContext_id() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CONTEXT_ID_ISSET_ID);
  }

  /** Returns true if field context_id is set (has been assigned a value) and false otherwise */
  public boolean isSetContext_id() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CONTEXT_ID_ISSET_ID);
  }

  public void setContext_idIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CONTEXT_ID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case CONTEXT_ID:
      if (value == null) {
        unsetContext_id();
      } else {
        setContext_id((java.lang.Long)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case CONTEXT_ID:
      return getContext_id();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case CONTEXT_ID:
      return isSetContext_id();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof scan_request)
      return this.equals((scan_request)that);
    return false;
  }

  public boolean equals(scan_request that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_context_id = true;
    boolean that_present_context_id = true;
    if (this_present_context_id || that_present_context_id) {
      if (!(this_present_context_id && that_present_context_id))
        return false;
      if (this.context_id != that.context_id)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(context_id);

    return hashCode;
  }

  @Override
  public int compareTo(scan_request other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetContext_id()).compareTo(other.isSetContext_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContext_id()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.context_id, other.context_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("scan_request(");
    boolean first = true;

    sb.append("context_id:");
    sb.append(this.context_id);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class scan_requestStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public scan_requestStandardScheme getScheme() {
      return new scan_requestStandardScheme();
    }
  }

  private static class scan_requestStandardScheme extends org.apache.thrift.scheme.StandardScheme<scan_request> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, scan_request struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CONTEXT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.context_id = iprot.readI64();
              struct.setContext_idIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, scan_request struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(CONTEXT_ID_FIELD_DESC);
      oprot.writeI64(struct.context_id);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class scan_requestTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public scan_requestTupleScheme getScheme() {
      return new scan_requestTupleScheme();
    }
  }

  private static class scan_requestTupleScheme extends org.apache.thrift.scheme.TupleScheme<scan_request> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, scan_request struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetContext_id()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetContext_id()) {
        oprot.writeI64(struct.context_id);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, scan_request struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.context_id = iprot.readI64();
        struct.setContext_idIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}


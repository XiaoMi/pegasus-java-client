/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * <p>DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package com.xiaomi.infra.pegasus.base;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(
    value = "Autogenerated by Thrift Compiler (0.11.0)",
    date = "2020-01-07")
public class request_meta
    implements com.xiaomi.infra.pegasus.thrift.TBase<request_meta, request_meta._Fields>,
        java.io.Serializable,
        Cloneable,
        Comparable<request_meta> {
  private static final com.xiaomi.infra.pegasus.thrift.protocol.TStruct STRUCT_DESC =
      new com.xiaomi.infra.pegasus.thrift.protocol.TStruct("request_meta");

  private static final com.xiaomi.infra.pegasus.thrift.protocol.TField APP_ID_FIELD_DESC =
      new com.xiaomi.infra.pegasus.thrift.protocol.TField(
          "app_id", com.xiaomi.infra.pegasus.thrift.protocol.TType.I32, (short) 1);
  private static final com.xiaomi.infra.pegasus.thrift.protocol.TField PARTITION_INDEX_FIELD_DESC =
      new com.xiaomi.infra.pegasus.thrift.protocol.TField(
          "partition_index", com.xiaomi.infra.pegasus.thrift.protocol.TType.I32, (short) 2);
  private static final com.xiaomi.infra.pegasus.thrift.protocol.TField CLIENT_TIMEOUT_FIELD_DESC =
      new com.xiaomi.infra.pegasus.thrift.protocol.TField(
          "client_timeout", com.xiaomi.infra.pegasus.thrift.protocol.TType.I32, (short) 3);
  private static final com.xiaomi.infra.pegasus.thrift.protocol.TField PARTITION_HASH_FIELD_DESC =
      new com.xiaomi.infra.pegasus.thrift.protocol.TField(
          "partition_hash", com.xiaomi.infra.pegasus.thrift.protocol.TType.I64, (short) 4);
  private static final com.xiaomi.infra.pegasus.thrift.protocol.TField
      IS_BACKUP_REQUEST_FIELD_DESC =
          new com.xiaomi.infra.pegasus.thrift.protocol.TField(
              "is_backup_request", com.xiaomi.infra.pegasus.thrift.protocol.TType.BOOL, (short) 5);

  private static final com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory
      STANDARD_SCHEME_FACTORY = new request_metaStandardSchemeFactory();
  private static final com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY =
      new request_metaTupleSchemeFactory();

  public int app_id; // required
  public int partition_index; // required
  public int client_timeout; // required
  public long partition_hash; // required
  public boolean is_backup_request; // required

  /**
   * The set of fields this struct contains, along with convenience methods for finding and
   * manipulating them.
   */
  public enum _Fields implements com.xiaomi.infra.pegasus.thrift.TFieldIdEnum {
    APP_ID((short) 1, "app_id"),
    PARTITION_INDEX((short) 2, "partition_index"),
    CLIENT_TIMEOUT((short) 3, "client_timeout"),
    PARTITION_HASH((short) 4, "partition_hash"),
    IS_BACKUP_REQUEST((short) 5, "is_backup_request");

    private static final java.util.Map<java.lang.String, _Fields> byName =
        new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /** Find the _Fields constant that matches fieldId, or null if its not found. */
    public static _Fields findByThriftId(int fieldId) {
      switch (fieldId) {
        case 1: // APP_ID
          return APP_ID;
        case 2: // PARTITION_INDEX
          return PARTITION_INDEX;
        case 3: // CLIENT_TIMEOUT
          return CLIENT_TIMEOUT;
        case 4: // PARTITION_HASH
          return PARTITION_HASH;
        case 5: // IS_BACKUP_REQUEST
          return IS_BACKUP_REQUEST;
        default:
          return null;
      }
    }

    /** Find the _Fields constant that matches fieldId, throwing an exception if it is not found. */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null)
        throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /** Find the _Fields constant that matches name, or null if its not found. */
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
  private static final int __APP_ID_ISSET_ID = 0;
  private static final int __PARTITION_INDEX_ISSET_ID = 1;
  private static final int __CLIENT_TIMEOUT_ISSET_ID = 2;
  private static final int __PARTITION_HASH_ISSET_ID = 3;
  private static final int __IS_BACKUP_REQUEST_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<
          _Fields, com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData>
      metaDataMap;

  static {
    java.util.Map<_Fields, com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData> tmpMap =
        new java.util.EnumMap<_Fields, com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData>(
            _Fields.class);
    tmpMap.put(
        _Fields.APP_ID,
        new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData(
            "app_id",
            com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT,
            new com.xiaomi.infra.pegasus.thrift.meta_data.FieldValueMetaData(
                com.xiaomi.infra.pegasus.thrift.protocol.TType.I32)));
    tmpMap.put(
        _Fields.PARTITION_INDEX,
        new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData(
            "partition_index",
            com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT,
            new com.xiaomi.infra.pegasus.thrift.meta_data.FieldValueMetaData(
                com.xiaomi.infra.pegasus.thrift.protocol.TType.I32)));
    tmpMap.put(
        _Fields.CLIENT_TIMEOUT,
        new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData(
            "client_timeout",
            com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT,
            new com.xiaomi.infra.pegasus.thrift.meta_data.FieldValueMetaData(
                com.xiaomi.infra.pegasus.thrift.protocol.TType.I32)));
    tmpMap.put(
        _Fields.PARTITION_HASH,
        new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData(
            "partition_hash",
            com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT,
            new com.xiaomi.infra.pegasus.thrift.meta_data.FieldValueMetaData(
                com.xiaomi.infra.pegasus.thrift.protocol.TType.I64)));
    tmpMap.put(
        _Fields.IS_BACKUP_REQUEST,
        new com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData(
            "is_backup_request",
            com.xiaomi.infra.pegasus.thrift.TFieldRequirementType.DEFAULT,
            new com.xiaomi.infra.pegasus.thrift.meta_data.FieldValueMetaData(
                com.xiaomi.infra.pegasus.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    com.xiaomi.infra.pegasus.thrift.meta_data.FieldMetaData.addStructMetaDataMap(
        request_meta.class, metaDataMap);
  }

  public request_meta() {}

  public request_meta(
      int app_id,
      int partition_index,
      int client_timeout,
      long partition_hash,
      boolean is_backup_request) {
    this();
    this.app_id = app_id;
    setApp_idIsSet(true);
    this.partition_index = partition_index;
    setPartition_indexIsSet(true);
    this.client_timeout = client_timeout;
    setClient_timeoutIsSet(true);
    this.partition_hash = partition_hash;
    setPartition_hashIsSet(true);
    this.is_backup_request = is_backup_request;
    setIs_backup_requestIsSet(true);
  }

  /** Performs a deep copy on <i>other</i>. */
  public request_meta(request_meta other) {
    __isset_bitfield = other.__isset_bitfield;
    this.app_id = other.app_id;
    this.partition_index = other.partition_index;
    this.client_timeout = other.client_timeout;
    this.partition_hash = other.partition_hash;
    this.is_backup_request = other.is_backup_request;
  }

  public request_meta deepCopy() {
    return new request_meta(this);
  }

  @Override
  public void clear() {
    setApp_idIsSet(false);
    this.app_id = 0;
    setPartition_indexIsSet(false);
    this.partition_index = 0;
    setClient_timeoutIsSet(false);
    this.client_timeout = 0;
    setPartition_hashIsSet(false);
    this.partition_hash = 0;
    setIs_backup_requestIsSet(false);
    this.is_backup_request = false;
  }

  public int getApp_id() {
    return this.app_id;
  }

  public request_meta setApp_id(int app_id) {
    this.app_id = app_id;
    setApp_idIsSet(true);
    return this;
  }

  public void unsetApp_id() {
    __isset_bitfield =
        com.xiaomi.infra.pegasus.thrift.EncodingUtils.clearBit(__isset_bitfield, __APP_ID_ISSET_ID);
  }

  /** Returns true if field app_id is set (has been assigned a value) and false otherwise */
  public boolean isSetApp_id() {
    return com.xiaomi.infra.pegasus.thrift.EncodingUtils.testBit(
        __isset_bitfield, __APP_ID_ISSET_ID);
  }

  public void setApp_idIsSet(boolean value) {
    __isset_bitfield =
        com.xiaomi.infra.pegasus.thrift.EncodingUtils.setBit(
            __isset_bitfield, __APP_ID_ISSET_ID, value);
  }

  public int getPartition_index() {
    return this.partition_index;
  }

  public request_meta setPartition_index(int partition_index) {
    this.partition_index = partition_index;
    setPartition_indexIsSet(true);
    return this;
  }

  public void unsetPartition_index() {
    __isset_bitfield =
        com.xiaomi.infra.pegasus.thrift.EncodingUtils.clearBit(
            __isset_bitfield, __PARTITION_INDEX_ISSET_ID);
  }

  /**
   * Returns true if field partition_index is set (has been assigned a value) and false otherwise
   */
  public boolean isSetPartition_index() {
    return com.xiaomi.infra.pegasus.thrift.EncodingUtils.testBit(
        __isset_bitfield, __PARTITION_INDEX_ISSET_ID);
  }

  public void setPartition_indexIsSet(boolean value) {
    __isset_bitfield =
        com.xiaomi.infra.pegasus.thrift.EncodingUtils.setBit(
            __isset_bitfield, __PARTITION_INDEX_ISSET_ID, value);
  }

  public int getClient_timeout() {
    return this.client_timeout;
  }

  public request_meta setClient_timeout(int client_timeout) {
    this.client_timeout = client_timeout;
    setClient_timeoutIsSet(true);
    return this;
  }

  public void unsetClient_timeout() {
    __isset_bitfield =
        com.xiaomi.infra.pegasus.thrift.EncodingUtils.clearBit(
            __isset_bitfield, __CLIENT_TIMEOUT_ISSET_ID);
  }

  /** Returns true if field client_timeout is set (has been assigned a value) and false otherwise */
  public boolean isSetClient_timeout() {
    return com.xiaomi.infra.pegasus.thrift.EncodingUtils.testBit(
        __isset_bitfield, __CLIENT_TIMEOUT_ISSET_ID);
  }

  public void setClient_timeoutIsSet(boolean value) {
    __isset_bitfield =
        com.xiaomi.infra.pegasus.thrift.EncodingUtils.setBit(
            __isset_bitfield, __CLIENT_TIMEOUT_ISSET_ID, value);
  }

  public long getPartition_hash() {
    return this.partition_hash;
  }

  public request_meta setPartition_hash(long partition_hash) {
    this.partition_hash = partition_hash;
    setPartition_hashIsSet(true);
    return this;
  }

  public void unsetPartition_hash() {
    __isset_bitfield =
        com.xiaomi.infra.pegasus.thrift.EncodingUtils.clearBit(
            __isset_bitfield, __PARTITION_HASH_ISSET_ID);
  }

  /** Returns true if field partition_hash is set (has been assigned a value) and false otherwise */
  public boolean isSetPartition_hash() {
    return com.xiaomi.infra.pegasus.thrift.EncodingUtils.testBit(
        __isset_bitfield, __PARTITION_HASH_ISSET_ID);
  }

  public void setPartition_hashIsSet(boolean value) {
    __isset_bitfield =
        com.xiaomi.infra.pegasus.thrift.EncodingUtils.setBit(
            __isset_bitfield, __PARTITION_HASH_ISSET_ID, value);
  }

  public boolean isIs_backup_request() {
    return this.is_backup_request;
  }

  public request_meta setIs_backup_request(boolean is_backup_request) {
    this.is_backup_request = is_backup_request;
    setIs_backup_requestIsSet(true);
    return this;
  }

  public void unsetIs_backup_request() {
    __isset_bitfield =
        com.xiaomi.infra.pegasus.thrift.EncodingUtils.clearBit(
            __isset_bitfield, __IS_BACKUP_REQUEST_ISSET_ID);
  }

  /**
   * Returns true if field is_backup_request is set (has been assigned a value) and false otherwise
   */
  public boolean isSetIs_backup_request() {
    return com.xiaomi.infra.pegasus.thrift.EncodingUtils.testBit(
        __isset_bitfield, __IS_BACKUP_REQUEST_ISSET_ID);
  }

  public void setIs_backup_requestIsSet(boolean value) {
    __isset_bitfield =
        com.xiaomi.infra.pegasus.thrift.EncodingUtils.setBit(
            __isset_bitfield, __IS_BACKUP_REQUEST_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
      case APP_ID:
        if (value == null) {
          unsetApp_id();
        } else {
          setApp_id((java.lang.Integer) value);
        }
        break;

      case PARTITION_INDEX:
        if (value == null) {
          unsetPartition_index();
        } else {
          setPartition_index((java.lang.Integer) value);
        }
        break;

      case CLIENT_TIMEOUT:
        if (value == null) {
          unsetClient_timeout();
        } else {
          setClient_timeout((java.lang.Integer) value);
        }
        break;

      case PARTITION_HASH:
        if (value == null) {
          unsetPartition_hash();
        } else {
          setPartition_hash((java.lang.Long) value);
        }
        break;

      case IS_BACKUP_REQUEST:
        if (value == null) {
          unsetIs_backup_request();
        } else {
          setIs_backup_request((java.lang.Boolean) value);
        }
        break;
    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
      case APP_ID:
        return getApp_id();

      case PARTITION_INDEX:
        return getPartition_index();

      case CLIENT_TIMEOUT:
        return getClient_timeout();

      case PARTITION_HASH:
        return getPartition_hash();

      case IS_BACKUP_REQUEST:
        return isIs_backup_request();
    }
    throw new java.lang.IllegalStateException();
  }

  /**
   * Returns true if field corresponding to fieldID is set (has been assigned a value) and false
   * otherwise
   */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
      case APP_ID:
        return isSetApp_id();
      case PARTITION_INDEX:
        return isSetPartition_index();
      case CLIENT_TIMEOUT:
        return isSetClient_timeout();
      case PARTITION_HASH:
        return isSetPartition_hash();
      case IS_BACKUP_REQUEST:
        return isSetIs_backup_request();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null) return false;
    if (that instanceof request_meta) return this.equals((request_meta) that);
    return false;
  }

  public boolean equals(request_meta that) {
    if (that == null) return false;
    if (this == that) return true;

    boolean this_present_app_id = true;
    boolean that_present_app_id = true;
    if (this_present_app_id || that_present_app_id) {
      if (!(this_present_app_id && that_present_app_id)) return false;
      if (this.app_id != that.app_id) return false;
    }

    boolean this_present_partition_index = true;
    boolean that_present_partition_index = true;
    if (this_present_partition_index || that_present_partition_index) {
      if (!(this_present_partition_index && that_present_partition_index)) return false;
      if (this.partition_index != that.partition_index) return false;
    }

    boolean this_present_client_timeout = true;
    boolean that_present_client_timeout = true;
    if (this_present_client_timeout || that_present_client_timeout) {
      if (!(this_present_client_timeout && that_present_client_timeout)) return false;
      if (this.client_timeout != that.client_timeout) return false;
    }

    boolean this_present_partition_hash = true;
    boolean that_present_partition_hash = true;
    if (this_present_partition_hash || that_present_partition_hash) {
      if (!(this_present_partition_hash && that_present_partition_hash)) return false;
      if (this.partition_hash != that.partition_hash) return false;
    }

    boolean this_present_is_backup_request = true;
    boolean that_present_is_backup_request = true;
    if (this_present_is_backup_request || that_present_is_backup_request) {
      if (!(this_present_is_backup_request && that_present_is_backup_request)) return false;
      if (this.is_backup_request != that.is_backup_request) return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + app_id;

    hashCode = hashCode * 8191 + partition_index;

    hashCode = hashCode * 8191 + client_timeout;

    hashCode =
        hashCode * 8191 + com.xiaomi.infra.pegasus.thrift.TBaseHelper.hashCode(partition_hash);

    hashCode = hashCode * 8191 + ((is_backup_request) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(request_meta other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetApp_id()).compareTo(other.isSetApp_id());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetApp_id()) {
      lastComparison =
          com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(this.app_id, other.app_id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison =
        java.lang.Boolean.valueOf(isSetPartition_index()).compareTo(other.isSetPartition_index());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartition_index()) {
      lastComparison =
          com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(
              this.partition_index, other.partition_index);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison =
        java.lang.Boolean.valueOf(isSetClient_timeout()).compareTo(other.isSetClient_timeout());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClient_timeout()) {
      lastComparison =
          com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(
              this.client_timeout, other.client_timeout);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison =
        java.lang.Boolean.valueOf(isSetPartition_hash()).compareTo(other.isSetPartition_hash());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPartition_hash()) {
      lastComparison =
          com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(
              this.partition_hash, other.partition_hash);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison =
        java.lang.Boolean.valueOf(isSetIs_backup_request())
            .compareTo(other.isSetIs_backup_request());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIs_backup_request()) {
      lastComparison =
          com.xiaomi.infra.pegasus.thrift.TBaseHelper.compareTo(
              this.is_backup_request, other.is_backup_request);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol iprot)
      throws com.xiaomi.infra.pegasus.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol oprot)
      throws com.xiaomi.infra.pegasus.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("request_meta(");
    boolean first = true;

    sb.append("app_id:");
    sb.append(this.app_id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("partition_index:");
    sb.append(this.partition_index);
    first = false;
    if (!first) sb.append(", ");
    sb.append("client_timeout:");
    sb.append(this.client_timeout);
    first = false;
    if (!first) sb.append(", ");
    sb.append("partition_hash:");
    sb.append(this.partition_hash);
    first = false;
    if (!first) sb.append(", ");
    sb.append("is_backup_request:");
    sb.append(this.is_backup_request);
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
      write(
          new com.xiaomi.infra.pegasus.thrift.protocol.TCompactProtocol(
              new com.xiaomi.infra.pegasus.thrift.transport.TIOStreamTransport(out)));
    } catch (com.xiaomi.infra.pegasus.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in)
      throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and
      // doesn't call the default constructor.
      __isset_bitfield = 0;
      read(
          new com.xiaomi.infra.pegasus.thrift.protocol.TCompactProtocol(
              new com.xiaomi.infra.pegasus.thrift.transport.TIOStreamTransport(in)));
    } catch (com.xiaomi.infra.pegasus.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class request_metaStandardSchemeFactory
      implements com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory {
    public request_metaStandardScheme getScheme() {
      return new request_metaStandardScheme();
    }
  }

  private static class request_metaStandardScheme
      extends com.xiaomi.infra.pegasus.thrift.scheme.StandardScheme<request_meta> {

    public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol iprot, request_meta struct)
        throws com.xiaomi.infra.pegasus.thrift.TException {
      com.xiaomi.infra.pegasus.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true) {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.STOP) {
          break;
        }
        switch (schemeField.id) {
          case 1: // APP_ID
            if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.I32) {
              struct.app_id = iprot.readI32();
              struct.setApp_idIsSet(true);
            } else {
              com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PARTITION_INDEX
            if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.I32) {
              struct.partition_index = iprot.readI32();
              struct.setPartition_indexIsSet(true);
            } else {
              com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CLIENT_TIMEOUT
            if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.I32) {
              struct.client_timeout = iprot.readI32();
              struct.setClient_timeoutIsSet(true);
            } else {
              com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PARTITION_HASH
            if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.I64) {
              struct.partition_hash = iprot.readI64();
              struct.setPartition_hashIsSet(true);
            } else {
              com.xiaomi.infra.pegasus.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // IS_BACKUP_REQUEST
            if (schemeField.type == com.xiaomi.infra.pegasus.thrift.protocol.TType.BOOL) {
              struct.is_backup_request = iprot.readBool();
              struct.setIs_backup_requestIsSet(true);
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

    public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol oprot, request_meta struct)
        throws com.xiaomi.infra.pegasus.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(APP_ID_FIELD_DESC);
      oprot.writeI32(struct.app_id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PARTITION_INDEX_FIELD_DESC);
      oprot.writeI32(struct.partition_index);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CLIENT_TIMEOUT_FIELD_DESC);
      oprot.writeI32(struct.client_timeout);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PARTITION_HASH_FIELD_DESC);
      oprot.writeI64(struct.partition_hash);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(IS_BACKUP_REQUEST_FIELD_DESC);
      oprot.writeBool(struct.is_backup_request);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }
  }

  private static class request_metaTupleSchemeFactory
      implements com.xiaomi.infra.pegasus.thrift.scheme.SchemeFactory {
    public request_metaTupleScheme getScheme() {
      return new request_metaTupleScheme();
    }
  }

  private static class request_metaTupleScheme
      extends com.xiaomi.infra.pegasus.thrift.scheme.TupleScheme<request_meta> {

    @Override
    public void write(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot, request_meta struct)
        throws com.xiaomi.infra.pegasus.thrift.TException {
      com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol oprot =
          (com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetApp_id()) {
        optionals.set(0);
      }
      if (struct.isSetPartition_index()) {
        optionals.set(1);
      }
      if (struct.isSetClient_timeout()) {
        optionals.set(2);
      }
      if (struct.isSetPartition_hash()) {
        optionals.set(3);
      }
      if (struct.isSetIs_backup_request()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetApp_id()) {
        oprot.writeI32(struct.app_id);
      }
      if (struct.isSetPartition_index()) {
        oprot.writeI32(struct.partition_index);
      }
      if (struct.isSetClient_timeout()) {
        oprot.writeI32(struct.client_timeout);
      }
      if (struct.isSetPartition_hash()) {
        oprot.writeI64(struct.partition_hash);
      }
      if (struct.isSetIs_backup_request()) {
        oprot.writeBool(struct.is_backup_request);
      }
    }

    @Override
    public void read(com.xiaomi.infra.pegasus.thrift.protocol.TProtocol prot, request_meta struct)
        throws com.xiaomi.infra.pegasus.thrift.TException {
      com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol iprot =
          (com.xiaomi.infra.pegasus.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.app_id = iprot.readI32();
        struct.setApp_idIsSet(true);
      }
      if (incoming.get(1)) {
        struct.partition_index = iprot.readI32();
        struct.setPartition_indexIsSet(true);
      }
      if (incoming.get(2)) {
        struct.client_timeout = iprot.readI32();
        struct.setClient_timeoutIsSet(true);
      }
      if (incoming.get(3)) {
        struct.partition_hash = iprot.readI64();
        struct.setPartition_hashIsSet(true);
      }
      if (incoming.get(4)) {
        struct.is_backup_request = iprot.readBool();
        struct.setIs_backup_requestIsSet(true);
      }
    }
  }

  private static <S extends com.xiaomi.infra.pegasus.thrift.scheme.IScheme> S scheme(
      com.xiaomi.infra.pegasus.thrift.protocol.TProtocol proto) {
    return (com.xiaomi.infra.pegasus.thrift.scheme.StandardScheme.class.equals(proto.getScheme())
            ? STANDARD_SCHEME_FACTORY
            : TUPLE_SCHEME_FACTORY)
        .getScheme();
  }
}

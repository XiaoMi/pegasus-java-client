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


public enum cas_check_type implements com.xiaomi.infra.pegasus.thrift.TEnum {
  CT_NO_CHECK(0),
  CT_VALUE_NOT_EXIST(1),
  CT_VALUE_NOT_EXIST_OR_EMPTY(2),
  CT_VALUE_EXIST(3),
  CT_VALUE_NOT_EMPTY(4),
  CT_VALUE_MATCH_ANYWHERE(5),
  CT_VALUE_MATCH_PREFIX(6),
  CT_VALUE_MATCH_POSTFIX(7),
  CT_VALUE_BYTES_LESS(8),
  CT_VALUE_BYTES_LESS_OR_EQUAL(9),
  CT_VALUE_BYTES_EQUAL(10),
  CT_VALUE_BYTES_GREATER_OR_EQUAL(11),
  CT_VALUE_BYTES_GREATER(12),
  CT_VALUE_INT_LESS(13),
  CT_VALUE_INT_LESS_OR_EQUAL(14),
  CT_VALUE_INT_EQUAL(15),
  CT_VALUE_INT_GREATER_OR_EQUAL(16),
  CT_VALUE_INT_GREATER(17);

  private final int value;

  private cas_check_type(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static cas_check_type findByValue(int value) { 
    switch (value) {
      case 0:
        return CT_NO_CHECK;
      case 1:
        return CT_VALUE_NOT_EXIST;
      case 2:
        return CT_VALUE_NOT_EXIST_OR_EMPTY;
      case 3:
        return CT_VALUE_EXIST;
      case 4:
        return CT_VALUE_NOT_EMPTY;
      case 5:
        return CT_VALUE_MATCH_ANYWHERE;
      case 6:
        return CT_VALUE_MATCH_PREFIX;
      case 7:
        return CT_VALUE_MATCH_POSTFIX;
      case 8:
        return CT_VALUE_BYTES_LESS;
      case 9:
        return CT_VALUE_BYTES_LESS_OR_EQUAL;
      case 10:
        return CT_VALUE_BYTES_EQUAL;
      case 11:
        return CT_VALUE_BYTES_GREATER_OR_EQUAL;
      case 12:
        return CT_VALUE_BYTES_GREATER;
      case 13:
        return CT_VALUE_INT_LESS;
      case 14:
        return CT_VALUE_INT_LESS_OR_EQUAL;
      case 15:
        return CT_VALUE_INT_EQUAL;
      case 16:
        return CT_VALUE_INT_GREATER_OR_EQUAL;
      case 17:
        return CT_VALUE_INT_GREATER;
      default:
        return null;
    }
  }
}

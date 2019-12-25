// Copyright (c) 2017, Xiaomi, Inc.  All rights reserved.
// This source code is licensed under the Apache License Version 2.0, which
// can be found in the LICENSE file in the root directory of this source tree.
package com.xiaomi.infra.pegasus.rpc;

import java.nio.ByteBuffer;

public class ThriftHeader {
  public static final int HEADER_LENGTH = 12;
  static final byte[] HEADER_TYPE = {'T', 'H', 'F', 'T'};
  public int body_length;
  public int meta_length;

  public byte[] toByteArray() {
    ByteBuffer bf = ByteBuffer.allocate(HEADER_LENGTH);
    bf.put(HEADER_TYPE);
    bf.putInt(body_length);
    bf.putInt(meta_length);
    return bf.array();
  }
}

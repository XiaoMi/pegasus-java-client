#!/usr/bin/env bash
#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#


SCRIPT_DIR=$(dirname "${BASH_SOURCE[0]}")
PROJECT_DIR=$(dirname "${SCRIPT_DIR}")
cd "${PROJECT_DIR}" || exit 1

SRC_FILES=(src/main/java/com/xiaomi/infra/pegasus/client/*.java
           src/main/java/com/xiaomi/infra/pegasus/client/request/batch/*.java
           src/main/java/com/xiaomi/infra/pegasus/client/request/range/*.java
           src/main/java/com/xiaomi/infra/pegasus/metrics/*.java
           src/main/java/com/xiaomi/infra/pegasus/rpc/*.java
           src/main/java/com/xiaomi/infra/pegasus/rpc/async/*.java
           src/main/java/com/xiaomi/infra/pegasus/operator/*.java
           src/main/java/com/xiaomi/infra/pegasus/tools/*.java
           src/main/java/com/xiaomi/infra/pegasus/rpc/interceptor/*.java
           src/main/java/com/xiaomi/infra/pegasus/base/*.java
           src/main/java/com/xiaomi/infra/pegasus/example/*.java
           src/main/java/com/xiaomi/infra/pegasus/security/*.java
           src/test/java/com/xiaomi/infra/pegasus/client/*.java
           src/test/java/com/xiaomi/infra/pegasus/metrics/*.java
           src/test/java/com/xiaomi/infra/pegasus/rpc/async/*.java
           src/test/java/com/xiaomi/infra/pegasus/tools/*.java
           src/test/java/com/xiaomi/infra/pegasus/base/*.java
           src/test/java/com/xiaomi/infra/pegasus/security/*.java
           )

if [ ! -f "${PROJECT_DIR}"/google-java-format-1.7-all-deps.jar ]; then
    wget https://github.com/google/google-java-format/releases/download/google-java-format-1.7/google-java-format-1.7-all-deps.jar
fi
java -jar "${PROJECT_DIR}"/google-java-format-1.7-all-deps.jar --replace "${SRC_FILES[@]}"

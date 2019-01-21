#!/usr/bin/env bash

set -e

# lint all scripts, abort if there's any warning.
function shellcheck_must_pass()
{
    echo "$1"
    if [[ $(shellcheck "$1") ]]; then
        echo "shellcheck $1 failed"
        shellcheck "$1"
        exit 1
    fi
}
shellcheck_must_pass ./scripts/format-all.sh
shellcheck_must_pass ./scripts/travis.sh

# start pegasus onebox environment
wget https://github.com/XiaoMi/pegasus/releases/download/v1.11.2/pegasus-server-1.11.2-a186d38-ubuntu-18.04-release.tar.gz
tar xvf pegasus-server-1.11.2-a186d38-ubuntu-18.04-release.tar.gz
cd pegasus-server-1.11.2-a186d38-ubuntu-release
./run.sh start_onebox
cd ../

mvn clean test

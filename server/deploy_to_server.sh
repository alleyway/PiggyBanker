#!/usr/bin/env bash
../gradlew build && scp build/libs/piggy*.jar zanzibar:/srv/piggygraph/
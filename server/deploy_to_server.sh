#!/usr/bin/env bash
../gradlew clean build && scp build/libs/piggy*.jar zanzibar:/srv/piggybanker/piggybanker_server.jar

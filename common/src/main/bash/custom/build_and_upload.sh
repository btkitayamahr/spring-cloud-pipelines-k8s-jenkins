#!/bin/bash

function build() {
    echo "I am executing a custom build function"
    mvn clean install -f ./${APP_NAME}/pom.xml
    docker image prune -a -f
}

#!/bin/bash

set -ex

pushd resource-utilisateur-git

    mvn clean install
    #cp target/service-utilisateur-*.jar ../output/
    #cp ci/service-utilisateur-1.0.0-SNAPSHOT.jar ../output
    #cp Dockerfile ../output

popd
#!/usr/bin/env bash

mvn package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/jpapan-library .
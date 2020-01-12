#!/usr/bin/env bash

docker run -i --rm -p 8080:8080 --net quarkuspanache_qpan-net --link librarydatasource --name jpa-library quarkus/jpapan-library
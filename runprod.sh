#!/usr/bin/env bash
docker-compose up -d
./buildlibrary.sh
./runlibrary.sh
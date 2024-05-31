#!/bin/bash

export CLUSTER_NAME=$(/usr/share/google/get_metadata_value attributes/dataproc-cluster-name)

export KAFKA-TOPIC-POWER="data-suply-topic"
export INPUT-DATA-DIR="data"
export INPUT-DATA-DIR-PROD="yellow_tripdata_result"
export SLEEP-TIME="50"
export HEADER-LENGTH="1"
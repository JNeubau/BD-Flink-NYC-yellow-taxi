#!/bin/bash

export INPUT_DATA_LOCATION="bucket-1-jn/proj2"

export CLUSTER_NAME=$(/usr/share/google/get_metadata_value attributes/dataproc-cluster-name)

export KAFKA_TOPIC_TAXI="taxi-input-topic"
export KAFKA_TOPIC_LOC="loc-input-topic"
export KAFKA_TOPIC_ANOMALY="anomaly-topic"

export INPUT_DATA_DIR="test"
export INPUT_DATA_DIR_PROD="yellow-tripdata-result"
export SLEEP_TIME=50
export HEADER_LENGTH=1
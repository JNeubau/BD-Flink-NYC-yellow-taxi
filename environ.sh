#!/bin/bash

export INPUT-DATA-LOCATION=bucket-1-jn/proj2

export CLUSTER_NAME=$(/usr/share/google/get_metadata_value attributes/dataproc-cluster-name)

export KAFKA-TOPIC-POWER=data-suply-topic
export INPUT-DATA-DIR=test
export INPUT-DATA-DIR-PROD=yellow_tripdata_result
export SLEEP-TIME=50
export HEADER-LENGTH=1
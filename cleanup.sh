source ./environ.sh

echo "Deleting Kafka topics..."
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --delete --topic ${KAFKA_TOPIC_TAXI}
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --delete --topic ${KAFKA_TOPIC_LOC}
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --delete --topic ${ANOMALY_OUTPUT_TOPIC}
echo 

echo "Delete repository..."
cd ..
rm -frv BD-Flink-NYC-yellow-taxi
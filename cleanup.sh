source ./environ.sh

echo "Deleting Kafka topics..."
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --delete --topic ${KAFKA_TOPIC_POWER}
echo 

echo "Delete repository..."
cd ..
rm -frv BD-Flink-NYC-yellow-taxi
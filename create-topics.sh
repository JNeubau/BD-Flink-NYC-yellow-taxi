source ./environ.sh

echo "Creating Kafka Topics"
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --create \
--replication-factor 1 --partitions 1 --topic kafka-topic-project2

echo "Available Topics:"
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --list
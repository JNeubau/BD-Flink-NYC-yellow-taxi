source ./environ.sh

echo "Creating topics"
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --create \
--replication-factor 1 --partitions 1 --topic ${KAFKA-TOPIC-POWER}

echo "Available Topics:"
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --list
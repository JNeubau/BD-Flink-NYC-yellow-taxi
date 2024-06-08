source ./environ.sh

kafka-console-consumer.sh --group my-consumer-group --bootstrap-server ${CLUSTER_NAME}-w-0:9092 --topic ${KAFKA_TOPIC_ANOMALY} --from-beginning
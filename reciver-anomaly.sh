source ./environ.sh

kafka-console-consumer.sh --bootstrap-server ${CLUSTER_NAME}-w-0:9092 \
    --topic ${KAFKA_TOPIC_ANOMALY} \
    --formatter kafka.tools.DefaultMessageFormatter \
    --property print.value=true \
    --property value.deserializer=org.apache.kafka.common.serialization.StringDeserializer
source ./environ.sh

java -cp /usr/lib/kafka/libs/*:KafkaProducer.jar com.example.bigdata.TestProducer \
  "$INPUT_DATA_DIR" \
  "$SLEEP_TIME" \
  "$KAFKA_TOPIC_POWER" \
  "$HEADER_LENGTH" \
  ${CLUSTER_NAME}-w-0:9092
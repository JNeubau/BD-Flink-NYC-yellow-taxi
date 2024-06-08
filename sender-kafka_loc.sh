source ./environ.sh

java -cp /usr/lib/kafka/libs/*:KafkaProducer.jar com.example.bigdata.TestProducer \
  "$INPUT_DATA_LOC_FILE" \
  "$SLEEP_TIME" \
  "$KAFKA_TOPIC_LOC" \
  "$HEADER_LENGTH" \
  ${CLUSTER_NAME}-w-0:9092
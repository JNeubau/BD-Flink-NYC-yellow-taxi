source ./environ.sh

java -cp /usr/lib/kafka/libs/*:KafkaProducer.jar \
com.example.bigdata.TestProducer \
${INPUT-DATA-DIR} ${SLEEP-TIME} ${KAFKA-TOPIC-POWER} ${HEADER-LENGTH} ${CLUSTER_NAME}-w-0:9092
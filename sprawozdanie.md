# Sprawozdanie - Apache Kafka - NYC Yellow Taxi 
Joanna Neubauer 148111

# KafkaProducer

## Uruchomienie

#### Argumenty
1. inputDir
2. sleepTimee
3. topicName
4. headerLength
5. bootstrapServers

### Komenda
temat sie nazywa "test"
folder z danymi nazywa się "data"

Utwórz temat kafki
```
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --create \
--replication-factor 1 --partitions 1 --topic test
```
Sprawdź dane kafki
```
- odczytaj port Zookeeper:
cat /usr/lib/kafka/config/server.properties | grep zookeeper.connect

- port nasłuchujący:
cat /usr/lib/kafka/config/server.properties | grep listeners
```

sprawdź dostępne tematy kafki
```
CLUSTER_NAME=$(/usr/share/google/get_metadata_value attributes/dataproc-cluster-name)
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --list
```

## Terminal odbiorczy
```
CLUSTER_NAME=$(/usr/share/google/get_metadata_value attributes/dataproc-cluster-name)
kafka-console-consumer.sh --group my-consumer-group \
--bootstrap-server ${CLUSTER_NAME}-w-0:9092 \
--topic test --from-beginning
```

```
CLUSTER_NAME=$(/usr/share/google/get_metadata_value attributes/dataproc-cluster-name)
java -cp /usr/lib/kafka/libs/*:KafkaProducer.jar \
com.example.bigdata.TestProducer \
data 15 test 0 ${CLUSTER_NAME}-w-0:9092
```
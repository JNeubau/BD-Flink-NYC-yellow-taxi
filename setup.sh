source ./environ.sh

echo "Killing yarn applications"
applications=$(yarn application --list | awk '{print $1}' | grep application)
if [ -z "$applications" ]; then
    echo "No yarn applications to kill"
else
    yarn application --list | awk '{print $1}' | grep application | xargs yarn application -kill
fi

echo "Deleting Kafka topics..."
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --delete --topic ${KAFKA_TOPIC_TAXI}
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --delete --topic ${KAFKA_TOPIC_LOC}
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --delete --topic ${KAFKA_TOPIC_ANOMALY}
echo ""

echo "Run Kafka creation"
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --create --replication-factor 1 --partitions 1 --topic ${KAFKA_TOPIC_TAXI}
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --create --replication-factor 1 --partitions 1 --topic ${KAFKA_TOPIC_LOC}
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --create --replication-factor 1 --partitions 1 --topic ${KAFKA_TOPIC_ANOMALY}
echo ""

echo "Available Topics:"
kafka-topics.sh --bootstrap-server ${CLUSTER_NAME}-w-1:9092 --list
echo ""
echo ""

echo "Delete files"
rm -rf "$INPUT_DATA_LOC_FILE"
rm -rf "$INPUT_DATA_DIR_TAXI"
rm -rf "$INPUT_DATA_DIR_TAXI.zip"

echo "Download csv from bucket"
hadoop fs -copyToLocal gs://${INPUT_DATA_LOCATION}/taxi_zone_lookup.csv "$INPUT_DATA_LOC_FILE" || exit
# hadoop fs -copyToLocal gs://${INPUT_DATA_LOCATION}/${INPUT_DATA_DIR_TAXI} "$INPUT_DATA_DIR_TAXI.zip" || exit
hadoop fs -copyToLocal gs://${INPUT_DATA_LOCATION}/${INPUT_DATA_DIR_TAXI} "$INPUT_DATA_DIR_TAXI" || exit
echo ""

echo "unzip"
# unzip -j "$INPUT_DATA_DIR_TAXI.zip" -d "$INPUT_DATA_DIR_TAXI" || exit
echo ""

echo "Downloading dependencies"
# cd ~
# wget https://repo1.maven.org/maven2/org/apache/flink/flink-connector-jdbc/1.16.1/flink-connector-jdbc-1.16.1.jar
# wget https://repo1.maven.org/maven2/org/apache/flink/flink-connector-kafka/1.16.1/flink-connector-kafka-1.16.1.jar
# wget https://repo1.maven.org/maven2/org/apache/flink/flink-clients/1.16.1/flink-clients-1.16.1.jar
# wget https://repo1.maven.org/maven2/org/apache/flink/flink-streaming-java/1.16.1/flink-streaming-java-1.16.1.jar
# wget https://repo1.maven.org/maven2/org/apache/flink/flink-core/1.16.1/flink-core-1.16.1.jar
# wget https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar
# sudo cp ~/*-*.jar /usr/lib/flink/lib/
wget https://repo1.maven.org/maven2/org/apache/flink/flink-connector-kafka/1.16.1/flink-connector-kafka-1.16.1.jar
wget https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar

wget https://repo1.maven.org/maven2/org/apache/flink/flink-connector-cassandra_2.12/1.16.1/flink-connector-cassandra_2.12-1.16.1.jar
sudo cp ~/*-*.jar /usr/lib/flink/lib/
echo ""

echo "Sending static data to kafka topic"
cat "$INPUT_DATA_LOC_FILE" | awk -F ',' 'NR>1{print $1 ":" $1 "," $2 "," $3 "," $4}' | kafka-console-producer.sh --bootstrap-server "$BOOTSTRAP_SERVERS" --topic "$KAFKA_TOPIC_LOC" --property key.separator=: --property parse.key=true
echo ""

echo "Starting mysql"
sudo apt-get update
sudo apt-get install docker-compose-plugin
docker compose down
docker compose up -d --wait

echo "Preparing cassandra schema"
docker exec -it cassandra cqlsh -e "CREATE KEYSPACE IF NOT EXISTS taxi_data WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};
                                    USE taxi_data;
                                    CREATE TABLE IF NOT EXISTS taxi_events_sink
                                    (
                                        borough                TEXT,
                                        from_val               TEXT,
                                        to_val                 TEXT,
                                        departures             BIGINT,
                                        arrivals               BIGINT,
                                        totalPassengersArr     BIGINT,
                                        totalPassengersDep     BIGINT,
                                        PRIMARY KEY ((borough), from_val, totalPassengers)
                                    );
                                    TRUNCATE taxi_data.taxi_events_sink;"

echo "Reset complete"

# echo "Preparing mysql schema"
# docker exec -it mymysql mysql -uroot -ppassword <<< "mysqladmin GRANT ALL ON streamdb TO 'user' IDENTIFIED BY 'password' WITH GRANT OPTION";

# docker exec -it mymysql mysql --user=user -ppassword <<< "CREATE TABLE IF NOT EXISTS taxi_events_sink
#                                     (
#                                         borough varchar(255),
#                                         from_val varchar(50),
#                                         to_val varchar(50),
#                                         departures integer, 
#                                         arrivals integer, 
#                                         totalPassengers integer, 
#                                         totalAmount integer)
#                                         PRIMARY KEY ((borough), from_val, to_val)
#                                     );"
#                                     # TRUNCATE crime_data.crime_aggregate;"

# echo "Reset complete"
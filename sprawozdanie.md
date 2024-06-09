# Sprawozdanie - Apache Flink - NYC Yellow Taxi 

## Prepare the environment (Producent; skrypty inicjujące i zasilający)
```
gcloud dataproc clusters create ${CLUSTER_NAME} \
--enable-component-gateway --region ${REGION} --subnet default \
--master-machine-type n1-standard-4 --master-boot-disk-size 50 \
--num-workers 2 --worker-machine-type n1-standard-2 --worker-boot-disk-size 50 \
--image-version 2.1-debian11 --optional-components ZOOKEEPER,DOCKER,FLINK \
--project ${PROJECT_ID} --max-age=3h \
--metadata "run-on-master=true" \
--initialization-actions gs://goog-dataproc-initialization-actions-${REGION}/kafka/kafka.sh
```

1. Download package from [https://github.com/JNeubau/BD-Flink-NYC-yellow-taxi.git]().
Unpack the files.
2. Make sure that source data is in your bucket in with the original name.
3. Execute``` chmod +x *.sh ```
4. Change vaiables in `environ.sh` file to match your data
- `INPUT_DATA_LOCATION` - your bucket
- `INPUT_DATA_LOC_FILE` - static input data
- `INPUT_DATA_DIR_TAXI` - folder with events

```
git clone https://github.com/JNeubau/BD-Flink-NYC-yellow-taxi.git
cd BD-Flink-NYC-yellow-taxi/
mv * ../
chmod +x *.sh
```
5. Run the  `setup.sh` script to prepare environment
6. Using `vim flink.properties` create a file and pase the following data, changing `CASSANDRA_HOST`, `BOOTSTRAP_SERVERS`, `taxiData.directoryPath`, `zoneFile.path` according to your data. CASSANDRA_HOST is value of cluster name with '-m' (ex: `pdb-cluster-m`) and BOOTSTRAP_SERVERS is value of cluster name with '-w-0:9092' (ex: `pdb-cluster-w-0:9092`)
```
taxiData.directoryPath = test/
zoneFile.path = taxi_zone_lookup.csv

taxiEvents.maxElements = 10000
taxiEvents.elementDelayMillis = 100

FLINK_ANOMALY_TIME = 4
FLINK_ANOMALY_PEOPLE = 1000
DELAY_VERSION = C

BOOTSTRAP_SERVERS = pdb-cluster-w-0:9092
TAXI_INPUT_TOPIC = taxi-input-topic
LOC_INPUT_TOPIC = loc-input-topic
ANOMALY_OUTPUT_TOPIC = anomaly-topic
KAFKA_GROUP_ID = kafka-group-id
#FLINK_DELAY = flink-delay
#FLINK_CHECKPOINT_DIR = flink-checkpoint-dir

CASSANDRA_HOST = pdb-cluster-m
CASSANDRA_PORT = 9042
```
7. Execute these commands:
```
mkdir -p src/main/resources/
mv flink.properties src/main/resources/
```
8. Upload the jar file `TaxiEventsAnalysis.jar` and move it to toyr working directory.
9. In one of the terminals run the `sender-kafka_taxi.sh` script to start sending data via kafka producer.

- setup.sh
![setup.sh](images/image-1.png)
- reciver-kafka.sh


10. To check if sources work these commands can be used:
- for event data
```
source ./environ.sh (after running `sender-kafka_taxi.sh`)
kafka-console-consumer.sh --group my-consumer-group \
--bootstrap-server ${CLUSTER_NAME}-w-0:9092 \
--topic ${KAFKA_TOPIC_TAXI} --from-beginning
```

- for static data (after running `setup.sh`)
```
source ./environ.sh
kafka-console-consumer.sh --group my-consumer-group \
--bootstrap-server ${CLUSTER_NAME}-w-0:9092 \
--topic ${KAFKA_TOPIC_LOC} --from-beginning
```

- example (static):
![reciver-kafka.sh](images/image.png)

## Transformations (Utrzymanie obrazu czasu rzeczywistego – transformacje)

## A delay (Utrzymanie obrazu czasu rzeczywistego – obsługa trybu A)

## C delay (Utrzymanie obrazu czasu rzeczywistego – obsługa trybu C)

## Anomalies (Wykrywanie anomalii)

## Examples
![ResultsCandAnomalies](images/OutputCandAnomales.png)
The above piece of output is generated for ***C type*** and ***Anomalie***, both printed to console output (in InteliJ) for ***200 000*** elements in the input data. A ***4-hour*** time window during which the difference between people, who departed the borough and people who arravied to it, is over ***4000*** people is considered an anomaly

![ResultsA](images/resultsA.png)
The above piece is the output generated for ***A type***, printed to the console (in InteliJ). 

The diffference between teh two outputs can be seen easily.

## Run the script (Program przetwarzający strumienie danych; skrypt uruchamiający)
`./run.sh` 

## Output

### Create (Miejsce utrzymywania obrazów czasu rzeczywistego – skrypt tworzący)
Last part of initialization schema takes care of setting up the Cassandra database.
```
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
            PRIMARY KEY ((borough), from_val, totalPassengersArr, totalPassengersDep)
        );
        TRUNCATE taxi_data.taxi_events_sink;"

echo "Reset complete"
```

### Characteristics (Miejsce utrzymywania obrazów czasu rzeczywistego – cechy)
Why Cassandra?
- available conncetor for Flink, making transfering of data simple
- scalability - NoSQL database, that can be easily expanded to match app requirements
- efficiency with writing data - perfect for processing data in real time and frequest data changes

### Read (Konsument: skrypt odczytujący wyniki przetwarzania)
`show_output.sh` file contains command to show contents of Cassandra database as shown below:
![read_output](images/Cassandra_output.png)
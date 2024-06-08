# Sprawozdanie - Apache Flink - NYC Yellow Taxi 

## Cluster
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

## Prepare the environment
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
6. In one of the terminal runthe `sender-kafka_taxi.sh` script to start sending data via kafka producer.
```
./setup.sh
./sender-kafka_taxi.sh
```

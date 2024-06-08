# Report - Apache Kafka - NYC Yellow Taxi 
Joanna Neubauer 148111

## Initialize cluster
```
gcloud dataproc clusters create ${CLUSTER_NAME} \
--enable-component-gateway --bucket ${BUCKET_NAME} \
--region ${REGION} --subnet default \
--master-machine-type n1-standard-4 --master-boot-disk-size 50 \
--num-workers 2 --worker-machine-type n1-standard-2 --worker-boot-disk-size 50 \
--image-version 2.1-debian11 --optional-components FLINK,DOCKER,ZOOKEEPER \
--project ${PROJECT_ID} --max-age=4h \
--metadata "run-on-master=true" \
--initialization-actions \
gs://goog-dataproc-initialization-actions-${REGION}/kafka/kafka.sh
```

## Download repository code
```
git clone https://github.com/JNeubau/BD-Flink-NYC-yellow-taxi.git
cd BD-Flink-NYC-yellow-taxi/
```

## Add exec permissions to scripts
```
chmod +x *.sh
```

## Run the setup scripts
### Download all necessary data
```
./setup.sh
```

<!-- ### Assign environmental variables
```
./environ.sh
``` -->

<!-- ### Create Kafka topics
```
./create-topics.sh
``` -->

### Turn on listener terminal
```
./reciver-kafka.sh
```

### Turn on sender terminal
```
./sender-kafka.sh
```

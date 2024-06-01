# Report - Apache Kafka - NYC Yellow Taxi 
Joanna Neubauer 148111

## Initialize cluster
```
gcloud dataproc clusters create ${CLUSTER_NAME} \
--enable-component-gateway --bucket ${BUCKET_NAME} --region ${REGION} --subnet default \
--master-machine-type n1-standard-4 --master-boot-disk-size 50 \
--num-workers 2 --worker-machine-type n1-standard-2 --worker-boot-disk-size 50 \
--image-version 2.1-debian11 --optional-components DOCKER,ZOOKEEPER \
--project ${PROJECT_ID} --max-age=3h \
--metadata "run-on-master=true" \
--initialization-actions \
gs://goog-dataproc-initialization-actions-${REGION}/kafka/kafka.sh
```

## Add exec permissions to scripts
```
chmod +x *.sh
```

## Run the setup scripts
## Download all necessary data
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
./kafka-reciver.sh
```

### Turn on sender terminal
```
./kafka-sender.sh
```

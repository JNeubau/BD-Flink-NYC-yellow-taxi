# Repoer - Apache Kafka - NYC Yellow Taxi 
Joanna Neubauer 148111

## 1. Download all necessary data
```
git clone https://github.com/JNeubau/BD-Flink-NYC-yellow-taxi.git
hadoop fs -copyToLocal gs://bucket-1-jn/proj2
```

## 2. Add exec permissions to scripts
```
chmod +x *.sh
```

## 3. Run the setup scripts
### Assign environmental variables
```
./environ.sh
```

### Create Kafka topics
```
./create-topics.sh
```

### Turn on listener terminal
```
./kafka-reciver.sh
```

### Turn on sender terminal
```
./kafka-sender.sh
```

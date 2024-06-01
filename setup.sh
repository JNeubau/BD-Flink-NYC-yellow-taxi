source ./environ.sh

echo "Download repository code"
git clone https://github.com/JNeubau/BD-Flink-NYC-yellow-taxi.git

echo "Download csv from bucket"
hadoop fs -copyToLocal gs://${INPUT-DATA-LOCATION}

echo "unzip"

echo "Run Kafka creation"
./create-topics.sh
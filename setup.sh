source ./environ.sh

echo "Download csv from bucket"
hadoop fs -copyToLocal gs://${INPUT-DATA-LOCATION}

echo "unzip"

echo "Run Kafka creation"
./create-topics.sh
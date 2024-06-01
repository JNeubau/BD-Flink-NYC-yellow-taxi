source ./environ.sh

echo "Download csv from bucket"
hadoop fs -copyToLocal gs://${INPUT_DATA_LOCATION}

echo "unzip"

echo "Run Kafka creation"
./create-topics.sh
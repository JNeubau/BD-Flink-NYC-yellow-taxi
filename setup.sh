source ./environ.sh

echo "Download csv from bucket"
hadoop fs -copyToLocal gs://${INPUT_DATA_LOCATION}
mkdir ${INPUT_DATA_DIR}
hadoop fs -copyToLocal gs://${INPUT_DATA_LOCATION}/${INPUT_DATA_DIR}
echo ""

echo "unzip"
echo ""

echo "Run Kafka creation"
./create-topics.sh
echo ""
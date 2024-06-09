source ./environ.sh

flink run -m yarn-cluster -p 2 -yjm 1024m -ytm 1024m -c com.example.bigdata.TaxiEventsAnalysis ./TaxiEventsAnalysis.jar
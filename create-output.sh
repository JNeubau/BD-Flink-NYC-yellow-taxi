source ./environ.sh

echo "Create datadir"
mkdir /tmp/datadir

echo "Run docker container"
docker run --name mymysql -v /tmp/datadir:/var/lib/mysql -p 6033:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:debian

sleep 10

echo "Connect to container:"
docker exec -it mymysql bash
mysql -uroot -pmy-secret-pw

CREATE USER 'streamuser'@'%' IDENTIFIED BY 'stream';
CREATE DATABASE IF NOT EXISTS streamdb CHARACTER SET utf8;
GRANT ALL ON streamdb.* TO 'streamuser'@'%';

mysql -u streamuser -p streamdb
# stream 

create table taxi_events_sink (
    borough varchar(255),
    from_val varchar(50),
    to_val varchar(50),
    departures integer, 
    arrivals integer, 
    totalPassengers integer, 
    totalAmount integer);

docker exec -it cassandra cqlsh -e "SELECT * FROM taxi_data.taxi_events_sink;"
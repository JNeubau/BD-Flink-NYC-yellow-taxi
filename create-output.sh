source ./environ.sh

echo "Ceate datadir"
mkdir /tmp/datadir

echo "Run docker container"
docker run --name mymysql -v /tmp/datadir:/var/lib/mysql -p 6033:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:debian

sleep 10

echo "Connect to container:"
docker exec -it mymysql bash
mysql -uroot -pmy-secret-pw
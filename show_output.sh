#!/bin/bash
docker exec -it cassandra cqlsh -e "SELECT * FROM taxi_data.taxi_events_sink;"
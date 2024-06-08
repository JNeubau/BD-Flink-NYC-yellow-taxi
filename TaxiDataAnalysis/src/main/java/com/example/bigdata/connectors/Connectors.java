package com.example.bigdata.connectors;

import com.example.bigdata.model.LocData;
import com.example.bigdata.model.ResultData;
import com.example.bigdata.model.TaxiEvent;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.cassandra.CassandraSink;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connectors {
    public static KafkaSource<TaxiEvent> getCrimesSource(ParameterTool properties) {
        return KafkaSource.<TaxiEvent>builder()
                .setBootstrapServers(properties.getRequired("BOOTSTRAP_SERVERS"))
                .setTopics(properties.getRequired("TAXI_INPUT_TOPIC"))
                .setGroupId(properties.getRequired("KAFKA_GROUP_ID"))
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setDeserializer(new TaxiDeserializator())
                .build();
    }

    public static KafkaSource<LocData> getIucrSource(ParameterTool properties) {
        return KafkaSource.<LocData>builder()
                .setBootstrapServers(properties.getRequired("BOOTSTRAP_SERVERS"))
                .setTopics(properties.getRequired("LOC_INPUT_TOPIC"))
                .setGroupId(properties.getRequired("KAFKA_GROUP_ID"))
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setDeserializer(new LocDeserializator())
                .build();
    }

    public static CassandraSink<ResultData> getCassandraAggSink(DataStream<ResultData> input, ParameterTool properties) throws Exception {
        return CassandraSink.addSink(input)
                .setHost(properties.get("CASSANDRA_HOST"), properties.getInt("CASSANDRA_PORT", 9042))
                .build();
    }

    public static SinkFunction<ResultData> getMySQLSink(ParameterTool properties) {

        JdbcStatementBuilder<ResultData> statementBuilder =
                new JdbcStatementBuilder<ResultData>() {
                    @Override
                    public void accept(PreparedStatement ps, ResultData data) throws SQLException {
                        ps.setString(1, data.getBorough());
                        ps.setString(2, data.getFrom().toString());
                        ps.setString(3, data.getTo().toString());
                        ps.setInt(4, data.getDepartures());
                        ps.setInt(5, data.getArrivals());
                        ps.setInt(6, data.getTotalPassengersArr());
                        ps.setInt(7, data.getTotalPassengersDep());
                    }
                };
        JdbcConnectionOptions connectionOptions = new
                JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                .withUrl(properties.getRequired("mysql.url"))
                .withDriverName(properties.getRequired("mysql.driver"))
                .withUsername(properties.getRequired("mysql.username"))
                .withPassword(properties.getRequired("mysql.password"))
                .build();

        JdbcExecutionOptions executionOptions = JdbcExecutionOptions.builder()
                .withBatchSize(100)
                .withBatchIntervalMs(200)
                .withMaxRetries(5)
                .build();

        SinkFunction<ResultData> jdbcSink =
                JdbcSink.sink("insert into taxi_events_sink" +
                                "(borough, from_val, to_val, " +
                                "departures, arrivals, totalPassengers, totalAmount) \n" +
                                "values (?, ?, ?, ?, ?, ?, ?)",
                        statementBuilder,
                        executionOptions,
                        connectionOptions);
        return jdbcSink;
    }

    public static KafkaSink<String> getAnomalySink(ParameterTool properties) {
        return KafkaSink.<String>builder()
                .setBootstrapServers(properties.getRequired("BOOTSTRAP_SERVERS"))
                .setRecordSerializer(KafkaRecordSerializationSchema.builder()
                        .setTopic(properties.get("ANOMALY_OUTPUT_TOPIC"))
                        .setValueSerializationSchema(new SimpleStringSchema())
                        .build()
                )
                .setDeliverGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
                .build();
    }
}

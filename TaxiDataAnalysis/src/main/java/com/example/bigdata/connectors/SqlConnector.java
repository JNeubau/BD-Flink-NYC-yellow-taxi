//package com.example.bigdata.connectors;
//
//import com.example.bigdata.model.ResultData;
//import org.apache.flink.api.java.utils.ParameterTool;
//import org.apache.flink.connector.jdbc.JdbcSink;
//import org.apache.flink.connector.jdbc.JdbcStatementBuilder;
//import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
//import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
//import org.apache.flink.streaming.api.functions.sink.SinkFunction;
//
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class SqlConnector {
//    public static SinkFunction<ResultData> getMySQLSink(ParameterTool properties) {
//
//        JdbcStatementBuilder<ResultData> statementBuilder =
//                new JdbcStatementBuilder<ResultData>() {
//                    @Override
//                    public void accept(PreparedStatement ps, ResultData data) throws SQLException {
//                        ps.setString(1, data.getBorough());
//                        ps.setString(2, data.getFrom().toString());
//                        ps.setString(3, data.getTo().toString());
//                        ps.setInt(4, data.getDepartures());
//                        ps.setInt(5, data.getArrivals());
//                        ps.setInt(6, data.getTotalPassengersArr());
//                        ps.setInt(7, data.getTotalPassengersDep());
//                    }
//                };
//        JdbcConnectionOptions connectionOptions = new
//                JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
//                .withUrl(properties.getRequired("mysql.url"))
//                .withDriverName(properties.getRequired("mysql.driver"))
//                .withUsername(properties.getRequired("mysql.username"))
//                .withPassword(properties.getRequired("mysql.password"))
//                .build();
//
//        JdbcExecutionOptions executionOptions = JdbcExecutionOptions.builder()
//                .withBatchSize(100)
//                .withBatchIntervalMs(200)
//                .withMaxRetries(5)
//                .build();
//
//        SinkFunction<ResultData> jdbcSink =
//                JdbcSink.sink("insert into taxi_events_sink" +
//                                "(borough, from, to, " +
//                                "departures, arrivals, totalPassengers, totalAmount) \n" +
//                                "values (?, ?, ?, ?, ?, ?, ?)",
//                        statementBuilder,
//                        executionOptions,
//                        connectionOptions);
//        return jdbcSink;
//    }
//}

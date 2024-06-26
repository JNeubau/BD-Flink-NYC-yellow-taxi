package com.example.bigdata;

import com.example.bigdata.connectors.Connectors;
import com.example.bigdata.connectors.TaxiEventSource;
import com.example.bigdata.model.*;
import com.example.bigdata.tools.EnrichWithLocData;
import com.example.bigdata.tools.GetAnomalyWindowFunction;
import com.example.bigdata.tools.GetFinalResultWindowFunction;
import com.example.bigdata.tools.TaxiLocAggregator;
import com.example.bigdata.tools.EveryEventTimeTrigger;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;

import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.EventTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.time.Duration;
import java.util.Objects;

public class TaxiEventsAnalysis {
    public static void main(String[] args) throws Exception {

        ParameterTool propertiesFromFile = ParameterTool.fromPropertiesFile("src/main/resources/flink.properties");
        ParameterTool propertiesFromArgs = ParameterTool.fromArgs(args);
        ParameterTool properties = propertiesFromFile.mergeWith(propertiesFromArgs);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 10000));
        env.enableCheckpointing(10000, CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setCheckpointTimeout(60000);

/*
        env.getCheckpointConfig().setCheckpointStorage(properties.getRequired("FLINK_CHECKPOINT_DIR"));

        DataStream<TaxiEvent> taxiEventsDS = env.fromSource(Connectors.getTaxiSource(properties), WatermarkStrategy.noWatermarks(), "Taxi Source");
        DataStream<LocData> locEventsDS = env.fromSource(Connectors.getLocSource(properties), WatermarkStrategy.noWatermarks(), "Loc Source");

    KafkaSource<String> source = KafkaSource.<String>builder()
            .setBootstrapServers("localhost:29092")
            .setTopics("flink-topic").setGroupId("first-group")
            .setStartingOffsets(OffsetsInitializer.earliest())
            .setValueOnlyDeserializer(new SimpleStringSchema())
            .build();

    DataStream<String> stringDataStream = env.fromSource(source, WatermarkStrategy.noWatermarks() , "Kafka Source");
*/

        /* read data from file */
        DataStream<TaxiEvent> taxiEventsDS = env.addSource(new TaxiEventSource(properties)).name( "taxi" );

        /* Join two types of data */
        DataStream<TaxiLocEvent> taxiLocEventsDS = taxiEventsDS
                .map(new EnrichWithLocData(properties.getRequired("zoneFile.path")))
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy.<TaxiLocEvent>forBoundedOutOfOrderness(Duration.ofDays(1))
                                .withTimestampAssigner(((taxiLocEvent, l) -> taxiLocEvent.getTimestamp().toInstant().
                                        getEpochSecond() * 1000))
                );


        /* Dokonywanie obliczeń dla
                 - każdej dzielnicy i
                 - każdego kolejnego dnia.
             Dla zmiennych:
                 - ile było wyjazdów (startStop = 0),
                 - ile było przyjazdów (startStop = 1)
                 - liczba pasażerów obsłużona dla przyjazdów
                 - liczba pasażerów obsłużona dla wyjazdów
*/
        String delay = properties.getRequired("DELAY_VERSION");

        DataStream<ResultData> taxiLocStatsDS = taxiLocEventsDS
                .keyBy(TaxiLocEvent::getBorough)
                .window(TumblingEventTimeWindows.of(Time.days(1)))
                .trigger((Objects.equals(delay, "A")) ? EveryEventTimeTrigger.create() : EventTimeTrigger.create())
                .aggregate(new TaxiLocAggregator(), new GetFinalResultWindowFunction());


        int anomalyTime = properties.getInt("FLINK_ANOMALY_TIME", 4);
        int anomalyPeople = properties.getInt("FLINK_ANOMALY_PEOPLE", 10000);

        DataStream<DeparturesAnomaly> anomalyOutput = taxiLocEventsDS
                .keyBy(TaxiLocEvent::getBorough)
                .window(SlidingEventTimeWindows.of(
                        Time.hours(anomalyTime),
                        Time.hours(1)))
                .aggregate(new TaxiLocAggregator(), new GetAnomalyWindowFunction())
                .filter(departuresAnomaly -> departuresAnomaly.getDifference() >= anomalyPeople);

        // print to console
        taxiLocStatsDS.print();
        anomalyOutput.print();

        // save to database
//        taxiLocStatsDS.addSink(Connectors.getMySQLSink(properties));
//        Connectors.getCassandraAggSink(taxiLocStatsDS, properties);
//        anomalyOutput.map(DeparturesAnomaly::toString).sinkTo(Connectors.getAnomalySink(properties));

        env.execute("Taxi Events Analysis");
    }
}

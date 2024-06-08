package com.example.bigdata;

import com.example.bigdata.connectors.TaxiEventSource;
import com.example.bigdata.model.*;
import com.example.bigdata.tools.EnrichWithLocData;
import com.example.bigdata.tools.GetFinalResultWindowFunction;
import com.example.bigdata.tools.TaxiLocAggregator;
import com.example.bigdata.windows.EveryEventTimeTrigger;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;

import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.EventTimeTrigger;

//import java.sql.Types;
import java.time.Duration;

public class TaxiEventsAnalysis {
    public static void main(String[] args) throws Exception {

        ParameterTool propertiesFromFile = ParameterTool.fromPropertiesFile("src/main/resources/flink.properties");
        ParameterTool propertiesFromArgs = ParameterTool.fromArgs(args);
        ParameterTool properties = propertiesFromFile.mergeWith(propertiesFromArgs);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 10000));
        env.enableCheckpointing(10000, CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setCheckpointTimeout(60000);
//        env.getCheckpointConfig().setCheckpointStorage(properties.getRequired("FLINK_CHECKPOINT_DIR"));

//        DataStream<TaxiEvent> crimesSource = env.fromSource(Connectors.getCrimesSource(properties), WatermarkStrategy.noWatermarks(), "Taxi Source");
//        DataStream<LocData> iucrSource = env.fromSource(Connectors.getIucrSource(properties), WatermarkStrategy.noWatermarks(), "Loc Source");
//
//    KafkaSource<String> source = KafkaSource.<String>builder()
//            .setBootstrapServers("localhost:29092")
//            .setTopics("flink-topic").setGroupId("first-group")
//            .setStartingOffsets(OffsetsInitializer.earliest())
//            .setValueOnlyDeserializer(new SimpleStringSchema())
//            .build();

//    DataStream<String> stringDataStream = env.fromSource(source, WatermarkStrategy.noWatermarks() , "Kafka Source");

// stream
// .keyBy(...) //<- utworzenie odpowiedniego typu strumienia
// .window(...) //<- wymagany: "window assigner"
// [.trigger(...)] //<- opcjonalny: "trigger" (lub domyślny trigger)
// [.evictor(...)] //<- opcjonalny: "evictor" (lub brak evictora)
// [.allowedLateness(...)] //<- opcjonalny: "obsługa danych zbyt późnych
// // o zadany dodatkowy czas" (lub zero)
// [.sideOutputLateData(...)] //<- opcjonalny: "znacznik dodatkowego wyjścia"
// // dla danych spóźnionych" (lub brak takiego dodatkowego wyjścia)
// .reduce/aggregate/apply() // <- wymagana: "funkcja okna"
// [.getSideOutput(...)] //<- opcjonalny: "znacznik bocznego strumienia" – odbiór
// // spóźnionych danych

        DataStream<TaxiEvent> taxiEventsDS = env.addSource(new TaxiEventSource(properties)).name( "taxi" );

        /* Join two types of data */
        DataStream<TaxiLocEvent> taxiLocEventsDS = taxiEventsDS
                .map(new EnrichWithLocData(properties.getRequired("zoneFile.path")))
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy.<TaxiLocEvent>forBoundedOutOfOrderness(Duration.ofDays(1))
                                .withTimestampAssigner(((taxiLocEvent, l) -> taxiLocEvent.getTimestamp().toInstant().
                                        getEpochSecond() * 1000))
                );


        /* TODO: Mamy już komplet potrzebnych informacji do wykonania naszych obliczeń.
                 Twoim celem jest dokonywanie obliczeń dla
                 - każdej dzielnicy i
                 - każdego kolejnego dnia.
                 Chcemy dowiedzieć się:
                 - ile było wyjazdów (startStop = 0),
                 - ile było przyjazdów (startStop = 1)
                 - jaka liczba pasażerów została obsłużona (dla przyjazdów)
                 - jaka sumeryczna kwota została ujszczona za przejazdy (tylko dla przyjazdów) ==>
                    jaka liczba pasażerów została obsłużona (dla wyjazdów)
*/
        String delay = "A";

        DataStream<ResultData> taxiLocStatsDS = taxiLocEventsDS
                .keyBy(TaxiLocEvent::getBorough)
                .window(TumblingEventTimeWindows.of(Time.days(1)))
                .trigger(delay.equals("A") ? EveryEventTimeTrigger.create() : EventTimeTrigger.create())
                .aggregate(new TaxiLocAggregator(), new GetFinalResultWindowFunction());

        taxiLocStatsDS.print();
//        taxiLocStatsDS.addSink(SqlConnector.getMySQLSink(properties));

        env.execute("Taxi Events Analysis");
    }
}

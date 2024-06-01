package com.example.bigdata;

import com.example.bigdata.connectors.Connectors;
import com.example.bigdata.model.*;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.ParameterTool;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.ZonedDateTime;

public class SensorDataAnalysis {
    public static void main(String[] args) throws Exception {
        ParameterTool propertiesFromFile = ParameterTool.fromPropertiesFile("src/main/resources/flink.properties");
        ParameterTool propertiesFromArgs = ParameterTool.fromArgs(args);
        ParameterTool properties = propertiesFromFile.mergeWith(propertiesFromArgs);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        /* TODO: UWAGA! Zanim rozpoczniesz implementację
                 - zapoznaj się z celem przetwarzania
                 - zapoznaj się zaimplementowanymi już klasami, a także plikiem parametrów
                 - postaraj się określić docelową rolę tych klas (zrozumieć cel ich implementacji)
         */

        /* TODO: Utwórz źródłowy strumień. Skorzystaj z klasy TaxiEventSource.
                 Załóż, że dane są uporządkowane względem znaczników czasowych

        DataStream<TaxiEvent> taxiEventsDS = */


        /* TODO: W strumieniu źródłowym brak jest informacji na temat dzielnicy,
                 jest ona dostępna w oddzielnym statycznym zbiorze danych.
                 Uzupełnij dane w strumieniu o nazwę dzielnicy.
                 Przy okazji pozbądź się danych nieistotnych z punktu widzenia celu przetwarzania

        DataStream<TaxiLocEvent> taxiLocEventsDS = */


        /* TODO: Mamy już komplet potrzebnych informacji do wykonania naszych obliczeń.
                 Twoim celem jest dokonywanie obliczeń dla
                 - każdej dzielnicy i
                 - każdego kolejnego dnia.
                 Chcemy dowiedzieć się:
                 - ile było wyjazdów (startStop = 0),
                 - ile było przyjazdów (startStop = 1)
                 - jaka liczba pasażerów została obsłużona (tylko dla przyjazdów)
                 - jaka sumeryczna kwota została ujszczona za przejazdy (tylko dla przyjazdów)

        DataStream<ResultData> taxiLocStatsDS = */


        /* TODO: Podłącz ujście. Wystarczy, że będziesz generował na dane na standardowe wyjście.

        taxiLocStatsDS.print(); */

        env.execute("Taxi Data Analysis");


//        ParameterTool propertiesFromFile = ParameterTool.fromPropertiesFile("src/main/resources/flink.properties");
//        ParameterTool propertiesFromArgs = ParameterTool.fromArgs(args);
//        ParameterTool properties = propertiesFromFile.mergeWith(propertiesFromArgs);
//
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//
////        DataStream<String> inputStream = env.fromElements(
////                "73,altitude,1591531560000",
////                "63,cadence,1591531560000",
////                "83,heartrate,1591531560000",
////                "21,rotations,1591531560000",
////                "33,speed,1591531560000",
////                "26,temperature,1591531560000",
////                "73,altitude,1591531561000",
////                "63,cadence,1591531561000",
////                "83,heartrate,1591531561000",
////                "21,rotations,1591531561000",
////                "33,speed,1591531561000",
////                "26,temperature,1591531561000");
//
//        // read file with zone data
//        DataStream<String> zoneInputStream = env.
//                fromSource(Connectors.getZoneSource(properties),
//                        WatermarkStrategy.noWatermarks(), "ZoneData");
//
//        DataStream<ZoneData> zoneDataDS = zoneInputStream.map((MapFunction<String, String[]>) txt -> txt.split(",") )
//                .filter(array -> array.length == 4)
//                .filter(array -> array[0].matches("\\d+"))
//                .map(array -> new ZoneData(Long.parseLong(array[0]), array[1], array[2], array[3]));
//
//        // read file with taxi data
//        DataStream<String> taxiInputStream = env.
//                fromSource(Connectors.getTaxiSource(properties),
//                        WatermarkStrategy.noWatermarks(), "TaxiData");
//
//        DataStream<TaxiData> taxiDataDS = taxiInputStream.map((MapFunction<String, String[]>) txt -> txt.split(",") )
//                .filter(array -> array.length == 9)
//                .filter(array -> array[0].matches("\\d+") && array[1].matches("\\d+")
//                        && array[3].matches("\\d+") && array[4].matches("\\d+"))
//                .map(array -> new TaxiData(Long.parseLong(array[0]), Integer.parseInt(array[1]),
//                        ZonedDateTime.parse(array[2]), Long.parseLong(array[3]), Integer.parseInt(array[4]),
//                        Double.parseDouble(array[5]), Integer.parseInt(array[6]), Double.parseDouble(array[7]),
//                        Integer.parseInt(array[8])));
//
//        DataStream<SensorDataAgg> taxiDataExtDS = taxiDataDS
//                .map(sd -> new TaxiDataAgg(
//                        sd(),
//                        sd.getValue(), sd.getTimestamp(), // max
//                        sd.getValue(), sd.getTimestamp(), // min
//                        1, sd.getValue()));
//        KeyedStream<SensorDataAgg, String> dataKeyedBySensor = sensorDataExtDS.keyBy(sd -> sd.getSensor());
//
//
////        DataStream<SensorDataAgg> sensorDataExtDS = sensorDataDS
////                .map(sd -> new SensorDataAgg(
////                        sd.getSensor(),
////                        sd.getValue(), sd.getTimestamp(), // max
////                        sd.getValue(), sd.getTimestamp(), // min
////                        1, sd.getValue()));
////
////        KeyedStream<SensorDataAgg, String> dataKeyedBySensor = sensorDataExtDS.keyBy(sd -> sd.getSensor());
////        DataStream<SensorDataAgg> result = dataKeyedBySensor.reduce(new MyReduceFunction());
//
//        zoneDataDS.print();
//        taxiDataDS.print();
////        result.print();
//
//        env.execute("SensorDataAnalysis");
    }
}

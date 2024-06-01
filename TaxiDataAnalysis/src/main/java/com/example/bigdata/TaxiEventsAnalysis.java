package com.example.bigdata;

import com.example.bigdata.connectors.TaxiEventSource;
import com.example.bigdata.model.TaxiEvent;
import com.example.bigdata.model.TaxiLocEvent;
import com.example.bigdata.tools.EnrichWithLocData;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.utils.ParameterTool;

import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class TaxiEventsAnalysis {
    public static void main(String[] args) throws Exception {

        ParameterTool propertiesFromFile = ParameterTool.fromPropertiesFile("src/main/resources/flink.properties");
        ParameterTool propertiesFromArgs = ParameterTool.fromArgs(args);
        ParameterTool properties = propertiesFromFile.mergeWith(propertiesFromArgs);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        /* TODO: Utwórz źródłowy strumień. Skorzystaj z klasy TaxiEventSource.
                 Załóż, że dane są uporządkowane względem znaczników czasowych
*/
        DataStream<TaxiEvent> taxiEventsDS = env.addSource(new TaxiEventSource(properties)).name( "taxi" );

        /* TODO: W strumieniu źródłowym brak jest informacji na temat dzielnicy,
                 jest ona dostępna w oddzielnym statycznym zbiorze danych.
                 Uzupełnij dane w strumieniu o nazwę dzielnicy.
                 Przy okazji pozbądź się danych nieistotnych z punktu widzenia celu przetwarzania
*/

        DataStream<TaxiLocEvent> taxiLocEventsDS = taxiEventsDS
                .map(new EnrichWithLocData(properties.getRequired("zoneFile.path")));


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


        /* TODO: Podłącz ujście. Wystarczy, że będziesz generował na dane na standardowe wyjście. */

        taxiLocEventsDS.print();
//        taxiLocStatsDS.print();

        env.execute("Taxi Events Analysis");
    }
}

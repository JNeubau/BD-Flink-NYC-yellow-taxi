package com.example.bigdata.model;

import org.apache.flink.api.java.utils.ParameterTool;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TaxiDataAnalysis {
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

        env.execute("Taxi Events Analysis");
    }
}

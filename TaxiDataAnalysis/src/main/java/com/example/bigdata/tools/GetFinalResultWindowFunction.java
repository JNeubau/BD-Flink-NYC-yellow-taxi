package com.example.bigdata.tools;

import com.example.bigdata.model.ResultData;
import com.example.bigdata.model.TaxiLocStats;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

public class GetFinalResultWindowFunction extends ProcessWindowFunction<TaxiLocStats, ResultData, String, TimeWindow> {
    @Override
    public void process(String key, Context context, Iterable<TaxiLocStats> input, Collector<ResultData> out) {
        int departures = 0;
        int arrivals = 0;
        int totalPassengersArr = 0;
        int totalPassengersDep = 0;

        for (TaxiLocStats stats : input) {
            departures += stats.getDepartures();
            arrivals += stats.getArrivals();
            totalPassengersArr += stats.getTotalPassengersArr();
            totalPassengersDep += stats.getTotalPassengersDep();
        }

        Instant windowStart = Instant.ofEpochMilli(context.window().getStart());
        Instant windowEnd = Instant.ofEpochMilli(context.window().getEnd());

        ResultData resultData = new ResultData(
                key,
                Date.from(windowStart),
                Date.from(windowEnd),
                departures,
                arrivals,
                totalPassengersArr,
                totalPassengersDep);

        out.collect(resultData);
    }
}

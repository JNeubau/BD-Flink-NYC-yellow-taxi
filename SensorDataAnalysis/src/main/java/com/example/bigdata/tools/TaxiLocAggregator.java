package com.example.bigdata.tools;

import com.example.bigdata.model.TaxiDataAgg;
import com.example.bigdata.model.TaxiZone;
import com.example.bigdata.model.TaxiZoneStats;
import org.apache.flink.api.common.functions.AggregateFunction;

public class TaxiLocAggregator implements AggregateFunction<TaxiZone, TaxiDataAgg, TaxiZoneStats> {
    @Override
    public TaxiDataAgg createAccumulator() {
        return new TaxiDataAgg();
    }

    @Override
    public TaxiDataAgg add(TaxiZone value, TaxiDataAgg accumulator) {
        if (value.getStartStop() == 0) {
            accumulator.addDeparture();
        } else if (value.getStartStop() == 1) {
            accumulator.addArrival(value.getPassengerCount(), value.getAmount());
        }
        return accumulator;
    }

    @Override
    public TaxiZoneStats getResult(TaxiDataAgg accumulator) {
        return accumulator.toStats();
    }

    @Override
    public TaxiDataAgg merge(TaxiDataAgg a, TaxiDataAgg b) {
        return a.merge(b);
    }
}


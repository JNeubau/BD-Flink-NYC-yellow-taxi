package com.example.bigdata.tools;

import com.example.bigdata.model.TaxiData;
import com.example.bigdata.model.TaxiZone;
import com.example.bigdata.model.ZoneData;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EnrichWithZoneData extends RichMapFunction<TaxiData, TaxiZone> {
    private final String locFilePath;
    private Map<Integer, ZoneData> locDataMap;

    public EnrichWithZoneData(String locFilePath) {
        this.locFilePath = locFilePath;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        locDataMap = loadLocDataMap();
    }

    @Override
    public TaxiZone map(TaxiData taxiEvent) throws Exception {
        Long locationID = taxiEvent.getLocationID();
        ZoneData locData = locDataMap.get(locationID);

        String borough = (locData != null) ? locData.getBorough() : "Unknown";

        return new TaxiZone(
                borough,
                taxiEvent.getLocationID(),
                taxiEvent.getTimestamp(),
                taxiEvent.getStart_stop(),
                taxiEvent.getPassenger_count(),
                taxiEvent.getAmount()
        );
    }

    private Map<Integer, ZoneData> loadLocDataMap() throws IOException {
        Map<Integer, ZoneData> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(locFilePath))) {
            String line;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Long locationID = Long.parseLong(parts[0]);
                    String borough = parts[1].replace("\"", "");
                    String zone = parts[2].replace("\"", "");
                    String serviceZone = parts[3].replace("\"", "");
                    ZoneData locData = new ZoneData(locationID, borough, zone, serviceZone);
                    map.put(Math.toIntExact(locationID), locData);
                }
            }
        }
        return map;
    }
}

package com.example.bigdata.connectors;

import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;

import java.time.Duration;

public class Connectors {
    public static FileSource<String> getTaxiSource(ParameterTool properties) {
        return FileSource
                .forRecordStreamFormat(new TextLineInputFormat(),
                        new Path(properties.getRequired("fileInputTaxi.uri")))
//                .monitorContinuously(Duration.ofMillis(
//                        Long.parseLong(properties.getRequired("fileInput.interval"))))
                .build();
    }

    public static FileSource<String> getZoneSource(ParameterTool properties) {
        return FileSource
                .forRecordStreamFormat(new TextLineInputFormat(),
                        new Path(properties.getRequired("fileInputZone.uri")))
                .build();
    }

}

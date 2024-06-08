package com.example.bigdata.connectors;

import com.example.bigdata.model.LocData;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

public class LocDeserializator implements KafkaRecordDeserializationSchema<LocData> {

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> consumerRecord, Collector<LocData> collector) throws IOException {
        LocData locData = new LocData();
    }

    @Override
    public TypeInformation<LocData> getProducedType() {
        return null;
    }
}

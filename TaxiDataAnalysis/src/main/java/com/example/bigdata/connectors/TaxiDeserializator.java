package com.example.bigdata.connectors;

import com.example.bigdata.model.TaxiEvent;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class TaxiDeserializator implements KafkaRecordDeserializationSchema<TaxiEvent> {

//    private DateTimeFormatter formatter;

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> consumerRecord, Collector<TaxiEvent> collector) throws IOException {
        try {
            TaxiEvent taxiEvent = TaxiEvent.fromString( new String(consumerRecord.value()));
            collector.collect(taxiEvent);
        } catch (ParseException e) {
            // Print malformed line to stderr
            System.err.println("Malformed line: " + Arrays.toString(consumerRecord.value()));
        }
    }

//    @Override
//    public void open(DeserializationSchema.InitializationContext context) throws Exception {
//        KafkaRecordDeserializationSchema.super.open(context);
////        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//    }

    @Override
    public TypeInformation<TaxiEvent> getProducedType() {
        return TypeInformation.of(TaxiEvent.class);
    }
}

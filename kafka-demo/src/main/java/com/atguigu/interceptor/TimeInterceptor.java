package com.atguigu.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

public class TimeInterceptor implements ProducerInterceptor<String, String> {
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        record.value();
        return new ProducerRecord<String, String>(record.topic(), record.partition(), record.timestamp(), record.key(), System.currentTimeMillis()+","+record.value());
    }

    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    public void close() {

    }

    public void configure(Map<String, ?> configs) {

    }
}

package com.atguigu.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class PartitionProducer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.atguigu.partitioner.MyPartitioner");


        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        for (int i = 0; i < 10; i++) {
            final String value = "atguigu" + i;

            producer.send(new ProducerRecord<String, String>("first", "atguigu", "atguigu--" + i), new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    System.out.println(value + "--" + metadata.partition() + "--" + metadata.offset());
                }
            });
        }

        producer.close();
    }

}

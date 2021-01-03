package com.atguigu.producer;

import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Properties;

public class MyProducer {

    public static void main(String[] args) {

        //1.创建kafka生产者的配置信息
        Properties properties = new Properties();

        //2.指定连接的kafka集群
        properties.put("bootstrap.servers", "hadoop102:9092");

        //3.ACK应答级别
        properties.put("acks", "all");

        //4.重试次数
        properties.put("retries", 3);

        //5.批次大小 一次发送多少大小的数据 16K
        properties.put("batch.size", 16384);

        //6.等待时间
        properties.put("linger.ms", 1);

        //7.RecordAccumulator缓冲区大小 32MB
        properties.put("buffer.memory", 33554432);

        properties.put("key.serializer", "org.apache.kafka.common.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
    }
}
package com.atguigu.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class CallBackProducer {
    public static void main(String[] args) {
        //1.创建配置信息
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        
        //2.创建生产者对象
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        //3.发送数据
        for (int i = 0; i < 10; i++) {
            final String value = "atguigu" + i;
            try {
                /*因为send方法返回的是一个Future对象，所以带get方法为同步发送，不带get为异步发送*/
                RecordMetadata recordMetadata = producer.send(new ProducerRecord<String, String>("a", "atguigu", value), new Callback() {
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        System.out.println(value + "--" + metadata.partition() + "--" + metadata.offset());
                    }
                }).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        //4.关闭资源
        producer.close();
    }
}

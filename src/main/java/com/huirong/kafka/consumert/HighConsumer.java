package com.huirong.kafka.consumert;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by huirong on 17-2-27.
 * 高级消费者, 不会保存offset
 */
public class HighConsumer {

    public static void main(String[] args) throws Exception{
        HighConsumer demo = new HighConsumer();
        KafkaConsumer consumer = demo.initConsumer();
        while (true){
            demo.Consume(consumer);
            Thread.sleep(1000);
        }

    }


    public KafkaConsumer<String, String> initConsumer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("zookeeper.connect", "localhost:2181");
        properties.put("group.id", "shbi");
        properties.put("enable.auto.commit", "true");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList("kafka"));
        return consumer;
    }

    //从topic 中读取数据并消费
    public void Consume(KafkaConsumer<String, String> consumer){
        ConsumerRecords<String, String> records = consumer.poll(10);
        for (ConsumerRecord record : records){
            System.out.println(record.value() + "|" + record.partition() + "|" + record.offset());
        }
    }
}



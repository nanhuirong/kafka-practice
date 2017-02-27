package com.huirong.kafka.consumert;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huirong on 17-2-27.
 */
public class MultiConsumer {
    private ExecutorService executor;
    private KafkaConsumer<String, String> consumer;
    private List<String> topics;

    public MultiConsumer() {
        this.consumer = initConsumer();
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

    public void Consumer(){
        //创建线程池
        executor = Executors.newFixedThreadPool(5);
        ConsumerRecords<String, String> records = this.consumer.poll(100);
        executor.submit(new ConsumerThread(records));
    }

    public static void main(String[] args) {
        MultiConsumer consumer = new MultiConsumer();
        while (true){
            consumer.Consumer();
        }
    }
}

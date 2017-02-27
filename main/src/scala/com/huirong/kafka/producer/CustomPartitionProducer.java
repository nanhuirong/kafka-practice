package com.huirong.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;

/**
 * Created by huirong on 17-2-27.
 */
public class CustomPartitionProducer {

    public static void main(String[] args) throws Exception{
        CustomPartitionProducer demo = new CustomPartitionProducer();
        Producer<String, String> producer = demo.initProducer();
        while (true){
            demo.publishMessage(producer, demo.generateData(), "kafka");
            Thread.sleep(1000);
        }
    }



    public Producer initProducer(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("acks", "all");
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("partitioner.class", "com.huirong.kafka.partition.simplePartitioner");
        Producer<String, String> producer = new KafkaProducer<String, String>(properties);
        return producer;
    }

    public void publishMessage(Producer producer, Map<String, String> data, String topic){
        ProducerRecord<String, String> record = null;
        for (Map.Entry<String, String> entry : data.entrySet()){
            record = new ProducerRecord<String, String>(topic, entry.getKey(), entry.getValue());
            producer.send(record);
        }
//        producer.close();
    }

    public Map<String, String> generateData(){
        Map<String, String> data = new HashMap<String, String>();
        Random random = new Random();
        for (int i = 0; i < 10000; i++){
            String clientIP = "192.168.14." + random.nextInt(255);
            String accessTime = new Date().toString();
            String message = accessTime + "|" + clientIP;
            data.put(clientIP, message);
        }
        return data;
    }
}

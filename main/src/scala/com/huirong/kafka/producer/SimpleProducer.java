package com.huirong.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by huirong on 17-2-27.
 */
public class SimpleProducer {

    public static void main(String[] args) throws Exception{
        SimpleProducer demo = new SimpleProducer();
        Producer producer = demo.initProducer();
        while (true){
            Thread.sleep(1000);
            demo.publishMessage(producer, demo.getData(), "kafka");
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

    public Map<String, String> getData(){
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < 100; i++){
            map.put(Integer.toString(i), Integer.toString(i + 100));
        }
        return map;
    }
}

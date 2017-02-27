package com.huirong.kafka.consumert;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

/**
 * Created by huirong on 17-2-27.
 */
public class ConsumerThread implements Runnable {
    private ConsumerRecords<String, String> records;

    public ConsumerThread(ConsumerRecords<String, String> records) {
        this.records = records;
    }

    public void run() {
        for (ConsumerRecord<String, String> record : records){
            System.out.println(Thread.currentThread().getName() + "|" + record.key() + "|" +
            record.value() + "|" + record.partition() + "|" + record.offset());
        }
    }
}

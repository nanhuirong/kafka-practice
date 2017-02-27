package com.huirong.kafka.partition;


import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * Created by huirong on 17-2-27.
 */
public class simplePartitioner implements Partitioner {

    public simplePartitioner() {
    }

    public void configure(Map<String, ?> map) {

    }

    //计算指定记录的分区
    public int partition(String topic, Object key, byte[] keyBytes,
                         Object value, byte[] valueBytes, Cluster cluster) {
        int partition = 0;
        String partitionerKey = (String) key;
        int offset = partitionerKey.lastIndexOf('.');
        if (offset > 0){
            partition = Integer.parseInt(partitionerKey.substring(offset + 1)) %
                    cluster.availablePartitionsForTopic(topic).size();
        }
        return partition;
    }

    public void close() {

    }
}

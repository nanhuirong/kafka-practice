package com.huirong.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Created by huirong on 17-2-27.
 */
public class MainTopology {
    public static final String ZOOKEEPR = "localhost:2181";
    public static final String KAFKA_TOPIC = "kafka";
    public static final String STORM_SPOUT_ID = "kafka_spout";
    public static final String STORM_BOLT_ID = "simple_bolt";


    public static void main(String[] args){
        //设置Spout
        BrokerHosts hosts = new ZkHosts(ZOOKEEPR);
        SpoutConfig conf = new SpoutConfig(hosts, KAFKA_TOPIC, "/tmp/zookeeper/" + KAFKA_TOPIC, STORM_SPOUT_ID);
        conf.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout spout = new KafkaSpout(conf);

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(STORM_SPOUT_ID, spout);
        builder.setBolt(STORM_BOLT_ID, new SimpleBolt());
        Config config = new Config();
        config.put("nimbus.host", "localhost");
        try {
            StormSubmitter.submitTopology("Simple", config, builder.createTopology());
//            LocalCluster cluster = new LocalCluster();
//            cluster.submitTopology("simple", config, builder.createTopology());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

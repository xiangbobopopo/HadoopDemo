package com.zqw.demo2;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//不带回掉的生产者
public class TestProducer1 {


    public static void main(String[] args) {

        Properties props = new Properties();
        // Kafka服务端的主机名和端口号
        props.put("bootstrap.servers", "hadoop21:9092");
        // 等待所有副本节点的应答
        props.put("acks", "all");
        // 消息发送最大尝试次数
        props.put("retries", 0);
        // 一批消息处理大小
        props.put("batch.size", 16384);
        // 请求延时
        props.put("linger.ms", 1);
        // 发送缓存区内存大小
        props.put("buffer.memory", 33554432);
        // key序列化
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //注册拦截器
        List<String> inList = new ArrayList<>();
        inList.add("com.zqw.demo2.TimeInterceptor");
        inList.add("com.zqw.demo2.CounterInterceptor");

        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, inList);

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 50; i++) {
            producer.send(new ProducerRecord<String, String>("test2", Integer.toString(i), "hello world-" + i));
        }

        producer.close();
    }

}

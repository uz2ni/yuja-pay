package com.yujapay.common;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class LoggingProducer {
    private final KafkaProducer<String, String> producer;
    private final String topic;
    public LoggingProducer(@Value("${kafka.clusters.bootstrapservers}") String bootstrapServers, // ${}한경변수는 각각의 서비스에서 가져옴(common의 것이 아님)
                           @Value("${logging.topic}")String topic) {

        // Producer Initialization ';'
        Properties props = new Properties();

        // kafka:29092
        props.put("bootstrap.servers", bootstrapServers); // 카프카 클러스터의 브로커에 프로듀스를 하게된다는 것. 브로커 여러개일 수있어서 bootstrap 이라고 함. 하지만 우리는 브로커 1개

        // "key:value"
        // 각 서버->외부 카프카 클러스터에 어떻게 구분해서 가지고 있을 것인가
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    // Kafka Cluster [key, value] Produce
    public void sendMessage(String key, String value) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
        producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                // System.out.println("Message sent successfully. Offset: " + metadata.offset());
            } else {
                exception.printStackTrace();
                // System.err.println("Failed to send message: " + exception.getMessage());
            }
        });
    }
}

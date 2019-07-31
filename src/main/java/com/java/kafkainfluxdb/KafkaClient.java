package com.java.kafkainfluxdb;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

@Service
public class KafkaClient {

    private static final Logger logger = LoggerFactory.getLogger(KafkaClient.class);

    @Autowired
    private InfluxDbClient influxDbClient;

    @Value("${kafka.topic_user_behavior}")
    private String topic;

    @PostConstruct
    public void init() {
        consumeMsg();
    }

    public void consumeMsg() {
        Properties props = new Properties();
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("kafka-client.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            if (records.isEmpty()) {
                continue;
            }
            for (ConsumerRecord<String, String> record : records) {
                logger.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
                Map<String,Object> recordMap = JacksonUtil.deserialize(record.value(), Map.class);
                influxDbClient.insertData(recordMap);
            }

        }
    }
}

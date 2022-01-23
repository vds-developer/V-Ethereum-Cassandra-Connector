package com.vconnector.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

public class KafkaPropertyBuilder {

    private final static String TOPIC = "default-topic";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private final static String APPLICATION_NAME = "VEthereum-Application";
    final Properties props = new Properties();

    public KafkaPropertyBuilder() {
       props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
       props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
       props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
       props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
       props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
       props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_NAME);
    }

    public KafkaPropertyBuilder bootstrapServer(String bootstrapServers) {
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return this;
    }

    public KafkaPropertyBuilder groupID(String groupID) {
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupID);
        return this;
    }

    public KafkaPropertyBuilder keyDeserializerClass(String keyDeserializerClass) {
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializerClass);
        return this;
    }

    public KafkaPropertyBuilder valueDeserializerClass(String valueDeserializerClass) {
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializerClass);
        return this;
    }

    public Properties build() {
        return props;
    }
}

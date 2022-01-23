
package com.vconnector.kafka;

import com.datastax.oss.driver.api.core.CqlSession;
import com.vconnector.cassandra.Connector;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;


public class KafkaConsumerExample {
    public static Consumer<Long, String> createConsumer(Properties properties, String topic) {
        // Create the consumer using props.
        final Consumer<Long, String> consumer =
                new KafkaConsumer<>(properties);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(topic));
        return consumer;
    }

    public static void runConsumer(Consumer<Long, String> consumer, Connector cassandraConnector) throws InterruptedException {
        final int giveUp = 5;   int noRecordsCount = 0;
        ConsumerRecords<Long, String> consumerRecords;
        consumer.seekToBeginning(consumer.assignment());
        while (true) {
            consumerRecords =
                    consumer.poll(Duration.ofSeconds(1));
            if (consumerRecords.count()==0) {
//                System.out.println(noRecordsCount);
//                noRecordsCount++;
//                if (noRecordsCount > giveUp) break;
//                else continue;
                continue;
            }
            consumerRecords.forEach(record -> {
                String val = record.value();
                String append = record.value().replace("\n" , "");
                cassandraConnector.appendJson("block", append);
                System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                        record.key(), record.value(),
                        record.partition(), record.offset());
            });

            consumer.commitAsync();
        }
    }
}

package com.vcli;

import com.datastax.oss.driver.api.core.CqlSession;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vconnector.cassandra.Connector;
import com.vconnector.kafka.KafkaConsumerExample;
import com.vconnector.kafka.KafkaPropertyBuilder;
import org.apache.commons.cli.*;
import org.apache.kafka.clients.consumer.Consumer;

import java.util.Properties;


public class main {
    private static String DEFAULTBOOTSTRAPSERVER = "127.0.0.1:9092";
    private static String DEFAULTCASSANDRASERVER = "127.0.0.1:9042";
    private static String TOPIC = "default-topic";
    private static String GROUP = "group1";

    public static void main(String[] args) {
        KafkaPropertyBuilder builder = new KafkaPropertyBuilder();
        Properties properties = builder
                .bootstrapServer("192.168.2.73:9092")
                .groupID("group1")
                .build();

        CommandLineParser parser = new DefaultParser();
        final Options options = new Options();

        Option bootStrapOption = Option.builder("b")
                .longOpt("bootstrapServer")
                .argName("bootstrapServer")
                .hasArg()
                .desc("The kafka Server")
                .required()
                .build();
        Option groupIdOption = Option.builder("g")
                .longOpt("groupID")
                .argName("groupID")
                .desc("The Kafka Group ID")
                .hasArg()
                .required()
                .build();
        Option topicOption = Option.builder("t")
                .longOpt("topic")
                .argName("topic")
                .desc("Kafka topic")
                .hasArg()
                .required()
                .build();
        Option cassandraServerOption = Option.builder("c")
                .longOpt("cassandraServer")
                .argName("cassandraServer")
                .desc("Cassandra Server")
                .hasArg()
                .required()
                .build();

        options.addOption(bootStrapOption);
        options.addOption(groupIdOption);
        options.addOption(topicOption);
        options.addOption(cassandraServerOption);



        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            String bootStrap = cmd.getOptionValue("bootstrapServer", DEFAULTBOOTSTRAPSERVER);
            String group = cmd.getOptionValue("groupID", GROUP);
            String topic = cmd.getOptionValue("topic", TOPIC);
            String cassandraServer = cmd.getOptionValue("cassandraServer", DEFAULTCASSANDRASERVER );

            String cassandraIP = cmd.getOptionValue("cassandraServer").split(":")[0];
            Integer cassandraPort = Integer.valueOf(cmd.getOptionValue("cassandraServer").split(":")[1]);


            Connector cassandraConnector = new Connector(cassandraIP, cassandraPort, "ethereum");
            cassandraConnector.createKeySpaceAndExecute("ethereum");
            cassandraConnector.createTableAndExecute("block");
            runKafka(bootStrap, group, topic, cassandraConnector);
        } catch (ParseException e) {
            System.err.println("Parsing Error. Reason: " + e.getMessage());
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("kafka topics", options);
        } catch (Exception e) {
            System.err.println("Parsing Error. Reason: " + e.getMessage());
        } finally {
            System.out.println("Finished");
        }
        System.out.printf("Done");
    }

    private static void runKafka(String bootstrapServer, String group, String topic, Connector cassandraConnector) {
        KafkaPropertyBuilder builder = new KafkaPropertyBuilder();
        Properties properties = builder
                .bootstrapServer(bootstrapServer)
                .groupID(group)
                .build();

        Consumer<Long, String> consumer = KafkaConsumerExample.createConsumer(properties, topic);
        try {
            KafkaConsumerExample.runConsumer(consumer, cassandraConnector);
        } catch (Exception e) {
            System.out.println("Error error: " + e.getMessage());
            consumer.close();
            e.printStackTrace();
        }
    }
}
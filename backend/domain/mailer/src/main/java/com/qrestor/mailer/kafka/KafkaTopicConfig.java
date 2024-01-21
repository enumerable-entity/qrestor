package com.qrestor.mailer.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import static com.qrestor.commons.kafka.KafkaTopics.MAILER_TOPIC;


@Configuration
public class KafkaTopicConfig {

    public static final String MAILER_CONSUMER_GROUP = "mailer-service";

    @Bean
    public NewTopic mailerTopic() {
        return TopicBuilder.name(MAILER_TOPIC).build();
    }
}

package com.qrestor.auth.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

import static com.qrestor.commons.kafka.KafkaTopics.USERS_TOPIC;

@Profile("!test")
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic usersSyncTopic() {
        return TopicBuilder.name(USERS_TOPIC).build();
    }

}

package com.qrestor.auth.kafka;

import com.qrestor.auth.kafka.dto.KafkaEmailSendRequestDTO;
import com.qrestor.auth.kafka.dto.UserKafkaSyncDTO;
import lombok.Getter;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;


@Configuration
public class KafkaTopicConfig {

    public static final String USERS_TOPIC = "users";
    public static final String MAILER_TOPIC = "mailer";

    @Getter
    private static final Map<Class, String> TOPICS = Map.of(
            UserKafkaSyncDTO.class, USERS_TOPIC,
            KafkaEmailSendRequestDTO.class, MAILER_TOPIC
    );

    @Bean
    public NewTopic usersSyncTopic() {
        return TopicBuilder.name(USERS_TOPIC).build();
    }
    @Bean
    public NewTopic mailerTopic() {
        return TopicBuilder.name(MAILER_TOPIC).build();
    }
}

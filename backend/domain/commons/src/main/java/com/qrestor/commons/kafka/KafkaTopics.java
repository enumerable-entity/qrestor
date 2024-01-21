package com.qrestor.commons.kafka;


import com.qrestor.commons.kafka.dto.KafkaEmailSendRequestDTO;
import com.qrestor.commons.kafka.dto.UserKafkaSyncDTO;
import lombok.Getter;

import java.util.Map;

public class KafkaTopics {

    public static final String USERS_TOPIC = "users";
    public static final String MAILER_TOPIC = "mailer";
    @Getter
    private static final Map<Class, String> TOPICS = Map.of(
            UserKafkaSyncDTO.class, USERS_TOPIC,
            KafkaEmailSendRequestDTO.class, MAILER_TOPIC
    );

    private KafkaTopics() {}
}

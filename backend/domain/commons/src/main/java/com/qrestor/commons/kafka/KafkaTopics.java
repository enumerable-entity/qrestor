package com.qrestor.commons.kafka;


import com.qrestor.models.dto.kafka.KafkaEmailSendRequestDTO;
import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import com.qrestor.models.dto.order.OrderDTO;
import lombok.Getter;

import java.util.Map;

public class KafkaTopics {

    public static final String USERS_TOPIC = "users";
    public static final String MAILER_TOPIC = "mailer";
    public static final String ORDERS_TOPIC = "orders";
    @Getter
    private static final Map<Class<?>, String> TOPICS = Map.of(
            UserKafkaSyncDTO.class, USERS_TOPIC,
            KafkaEmailSendRequestDTO.class, MAILER_TOPIC,
            OrderDTO.class, ORDERS_TOPIC
    );

    private KafkaTopics() {}
}

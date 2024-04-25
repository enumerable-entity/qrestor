package com.qrestor.orders.kafka;

import com.qrestor.commons.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class KafkaProducer<T> implements IKafkaProducer<T>{

    private final KafkaMessageSender<T> kafkaMessageSender;

    public void send(T message) {
        kafkaMessageSender.send(getTopic(message), message);
    }

    private String getTopic(T message) {
        String topicName = KafkaTopics.getTOPICS().get(message.getClass());
        if (topicName == null)
            throw new RuntimeException("Topic not found for class: " + message.getClass().getName());
        return topicName;
    }
}

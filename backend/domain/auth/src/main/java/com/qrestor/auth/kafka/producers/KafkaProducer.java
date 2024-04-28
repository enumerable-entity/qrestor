package com.qrestor.auth.kafka.producers;

import com.qrestor.auth.kafka.KafkaMessageSender;
import com.qrestor.commons.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class KafkaProducer<T> implements IKafkaProducer<T> {

    private final KafkaMessageSender<T> kafkaMessageSender;

    @Override
    public void send(T emailSendRequestDTO) {
        kafkaMessageSender.send(getTopic(emailSendRequestDTO), emailSendRequestDTO);
    }

    private String getTopic(T emailSendRequestDTO) {
        String topicName = KafkaTopics.getTOPICS().get(emailSendRequestDTO.getClass());
        if (topicName == null)
            throw new RuntimeException("Topic not found for class: " + emailSendRequestDTO.getClass().getName());
        return topicName;
    }
}

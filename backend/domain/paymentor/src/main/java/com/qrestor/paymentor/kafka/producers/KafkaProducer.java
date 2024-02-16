package com.qrestor.paymentor.kafka.producers;

import com.qrestor.commons.kafka.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer<T> {

    private final KafkaMessageSender<T> kafkaMessageSender;

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

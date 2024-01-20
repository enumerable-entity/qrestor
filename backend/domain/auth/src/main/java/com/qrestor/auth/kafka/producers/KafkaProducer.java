package com.qrestor.auth.kafka.producers;

import com.qrestor.auth.kafka.KafkaMessageSender;
import com.qrestor.auth.kafka.KafkaTopicConfig;
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
        String topicName = KafkaTopicConfig.getTOPICS().get(emailSendRequestDTO.getClass());
        if (topicName == null)
            throw new RuntimeException("Topic not found for class: " + emailSendRequestDTO.getClass().getName());
        return topicName;
    }
}

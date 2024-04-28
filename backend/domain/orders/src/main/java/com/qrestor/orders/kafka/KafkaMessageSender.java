package com.qrestor.orders.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("!test")
public class KafkaMessageSender<T> implements IKafkaMessageSender<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    @Override
    public void send(String topic,
                     T message) {
        log.debug("SENDING_MESSAGE_TO_TOPIC {}: {}", topic, message);
        kafkaTemplate.send(topic, message);
    }
}

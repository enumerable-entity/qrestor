package com.qrestor.mailer.kafka;

import com.qrestor.commons.kafka.dto.KafkaEmailSendRequestDTO;
import com.qrestor.mailer.sendgrid.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.qrestor.commons.kafka.KafkaTopics.MAILER_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMailerListener {

    private final MessageSender messageSender;

    @KafkaListener(topics = MAILER_TOPIC, groupId = KafkaTopicConfig.MAILER_CONSUMER_GROUP)
    public void consume(KafkaEmailSendRequestDTO message) {
        messageSender.sendEmail(message);
        log.info("MESSAGE CONSUMED: {}", message);
    }
}
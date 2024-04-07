package com.qrestor.mailer.kafka;

import com.qrestor.mailer.sendgrid.EmailMessageSender;
import com.qrestor.models.dto.kafka.KafkaEmailSendRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.qrestor.commons.kafka.KafkaTopics.MAILER_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMailerListener {

    private final EmailMessageSender emailMessageSender;

    @KafkaListener(topics = MAILER_TOPIC, groupId = KafkaTopicConfig.MAILER_CONSUMER_GROUP)
    public void consume(KafkaEmailSendRequestDTO message) {
        emailMessageSender.sendEmail(message);
        log.info("MESSAGE CONSUMED: {}", message);
    }
}
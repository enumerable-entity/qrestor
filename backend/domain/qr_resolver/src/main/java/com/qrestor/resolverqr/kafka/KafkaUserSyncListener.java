package com.qrestor.resolverqr.kafka;

import com.qrestor.commons.kafka.dto.UserKafkaSyncDTO;
import com.qrestor.resolverqr.user.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.qrestor.commons.kafka.KafkaTopics.USERS_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaUserSyncListener {

    private final SystemUserService systemUserService;

    @KafkaListener(topics = USERS_TOPIC)
    public void consume(UserKafkaSyncDTO message) {
        systemUserService.save(message);
        log.info("MESSAGE CONSUMED: {}", message);
    }
}
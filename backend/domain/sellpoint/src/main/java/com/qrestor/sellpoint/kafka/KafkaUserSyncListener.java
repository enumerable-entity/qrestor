package com.qrestor.sellpoint.kafka;

import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import com.qrestor.sellpoint.systemuser.service.SyncUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.qrestor.commons.kafka.KafkaTopics.USERS_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaUserSyncListener {

    private final SyncUserService syncUserService;

    @KafkaListener(topics = USERS_TOPIC)
    public void consume(UserKafkaSyncDTO message) {
        syncUserService.save(message);
        log.info("MESSAGE CONSUMED: {}", message);
    }
}
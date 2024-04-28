package com.qrestor.feedback.kafka;

import com.qrestor.feedback.systemuser.service.SyncUserService;
import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaUserSyncListener {

    private final SyncUserService syncUserService;

    //@KafkaListener(topics = USERS_TOPIC)
    public void consume(UserKafkaSyncDTO message) {
        syncUserService.save(message);
        log.info("MESSAGE CONSUMED: {}", message);
    }
}
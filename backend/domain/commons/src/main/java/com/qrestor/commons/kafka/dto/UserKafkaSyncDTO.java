package com.qrestor.commons.kafka.dto;

import java.util.UUID;

public record UserKafkaSyncDTO(Long id, UUID uuid, String username) {
}

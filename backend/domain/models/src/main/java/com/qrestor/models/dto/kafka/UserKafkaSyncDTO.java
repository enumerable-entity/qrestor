package com.qrestor.models.dto.kafka;

import java.util.UUID;

public record UserKafkaSyncDTO(Long id, UUID uuid, String username) {
}

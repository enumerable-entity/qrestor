package com.qrestor.auth.kafka.dto;

import java.util.UUID;

public record UserKafkaSyncDTO(UUID uuid, String username) {
}

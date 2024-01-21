package com.qrestor.commons.kafka.dto;

import java.util.UUID;

public record UserKafkaSyncDTO(UUID uuid, String username) {
}

package com.qrestor.models.dto;

import java.util.UUID;

public record DictionaryDTO<V>(UUID id, V name) {
}

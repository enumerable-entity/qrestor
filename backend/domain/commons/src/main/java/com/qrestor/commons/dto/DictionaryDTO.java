package com.qrestor.commons.dto;

import java.util.UUID;

public record DictionaryDTO<V>(UUID id, V name) {
}

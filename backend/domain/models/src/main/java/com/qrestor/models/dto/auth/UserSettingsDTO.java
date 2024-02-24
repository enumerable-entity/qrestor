package com.qrestor.models.dto.auth;

import java.io.Serializable;

public record UserSettingsDTO(Long id,
                              String theme,
                              String language,
                              String currency,
                              String timezone) implements Serializable {
}
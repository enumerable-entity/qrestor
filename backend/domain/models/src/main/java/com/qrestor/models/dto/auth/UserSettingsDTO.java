package com.qrestor.models.dto.auth;

import java.io.Serializable;

public record UserSettingsDTO(Long id,
                              String theme,
                              String language,
                              String currency,
                              String timezone,
                              boolean mfaEnabled,
                              String mfaType,
                              String mfaSecret,
                              String mfaRecoveryCodes,
                              String mfaRecoveryCodesUsed) implements Serializable {
}
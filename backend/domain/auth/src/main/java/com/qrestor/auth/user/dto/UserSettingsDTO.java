package com.qrestor.auth.user.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.qrestor.auth.user.entity.SystemUserSettings}
 */
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
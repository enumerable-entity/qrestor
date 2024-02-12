package com.qrestor.models.dto.auth;

public record UserDescriptorDTO(SystemUserDTO user,
                                UserInformationDTO information,
                                UserSettingsDTO settings,
                                AddressDTO address) {
}

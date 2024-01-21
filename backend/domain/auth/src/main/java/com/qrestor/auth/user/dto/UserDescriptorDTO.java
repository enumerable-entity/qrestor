package com.qrestor.auth.user.dto;

public record UserDescriptorDTO (SystemUserDTO user,
                                 UserInformationDTO information,
                                 UserSettingsDTO settings,
                                 AddressDTO address) {
}

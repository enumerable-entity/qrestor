package com.qrestor.auth.api.dto;

import com.qrestor.models.dto.auth.AddressDTO;
import com.qrestor.models.dto.auth.UserInformationDTO;
import com.qrestor.models.dto.auth.UserSettingsDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record RegistrationRequestDTO(@Size(min = 4, max = 20) String username,
                                     @Size(min = 8, max = 30) String password,
                                     @Email String email,
                                     UserInformationDTO information,
                                     UserSettingsDTO settings,
                                     AddressDTO address
) {
}

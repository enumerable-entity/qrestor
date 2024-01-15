package com.qrestor.auth.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record RegistrationRequestDTO(@Size(min = 4, max = 20) String username,
                                     @Size(min = 4, max = 30) String password,
                                     @Email String email) {
}

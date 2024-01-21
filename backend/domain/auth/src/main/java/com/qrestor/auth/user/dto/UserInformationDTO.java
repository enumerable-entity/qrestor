package com.qrestor.auth.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link com.qrestor.auth.user.entity.SystemUserInformationEntity}
 */
public record UserInformationDTO(Long id,
                                 @NotNull @NotBlank String firstName,
                                 @NotNull @NotBlank String lastName,
                                 String middleName,
                                 String phone,
                                 String profilePictureUrl) implements Serializable {
}
package com.qrestor.auth.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link com.qrestor.auth.user.entity.AddressEntity}
 */
public record AddressDTO(Long id,
                         @NotNull @NotBlank String number,
                         String address,
                         @NotNull @NotBlank String city,
                         String state,
                         @NotNull @NotBlank String zip,
                         @NotNull @NotBlank String country) implements Serializable {
}
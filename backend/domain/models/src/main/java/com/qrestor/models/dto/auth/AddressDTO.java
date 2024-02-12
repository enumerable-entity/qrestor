package com.qrestor.models.dto.auth;

import java.io.Serializable;

public record AddressDTO(Long id,
                         String number,
                         String address,
                         String city,
                         String state,
                         String zip,
                         String country) implements Serializable {
}
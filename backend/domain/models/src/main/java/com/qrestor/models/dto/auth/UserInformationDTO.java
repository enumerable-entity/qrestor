package com.qrestor.models.dto.auth;

import java.io.Serializable;

public record UserInformationDTO(Long id,
                                 String firstName,
                                 String lastName,
                                 String middleName,
                                 String phone,
                                 String profilePictureUrl,
                                 String businessName) implements Serializable {
}
package com.qrestor.models.dto.auth;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

public record SystemUserDTO(UUID uuid,
                            String username,
                            String email,
                            Collection<RoleDTO> authorities) implements Serializable {

    public record RoleDTO(Long id, String authority) implements Serializable {
    }
}
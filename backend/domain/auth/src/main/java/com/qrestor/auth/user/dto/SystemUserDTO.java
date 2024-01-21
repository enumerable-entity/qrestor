package com.qrestor.auth.user.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

/**
 * DTO for {@link com.qrestor.auth.user.entity.SystemUserEntity}
 */
public record SystemUserDTO(UUID uuid,
                            String username,
                            String email,
                            Collection<RoleDTO> authorities) implements Serializable {
    /**
     * DTO for {@link com.qrestor.auth.authority.SystemRoleEntity}
     */
    public record RoleDTO(Long id, String authority) implements Serializable {
    }
}
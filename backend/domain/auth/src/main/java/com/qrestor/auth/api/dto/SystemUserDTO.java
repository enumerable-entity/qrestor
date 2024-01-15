package com.qrestor.auth.api.dto;

import com.qrestor.auth.config.security.enums.SystemRole;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SystemUserDTO {
    private String username;
    private String password;
    private SystemRole role;
}

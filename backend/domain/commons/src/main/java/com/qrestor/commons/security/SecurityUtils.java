package com.qrestor.commons.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@UtilityClass
public class SecurityUtils {

    public String getPrincipalUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof QrestorPrincipal qrestorPrincipal) {
            return qrestorPrincipal.getName();
        }
        throw new RuntimeException("Principal is not QrestorPrincipal");
    }

    public UUID getPrincipalUUID() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof QrestorPrincipal qrestorPrincipal) {
            return qrestorPrincipal.getUuid();
        }
        throw new RuntimeException("Principal is not QrestorPrincipal");
    }
}

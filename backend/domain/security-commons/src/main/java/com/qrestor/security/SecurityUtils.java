package com.qrestor.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class SecurityUtils {

    public String getPrincipalUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof QrestorPrincipal qrestorPrincipal) {
            return qrestorPrincipal.getUsername();
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

    public List<String> getPrincipalRoles() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof QrestorPrincipal qrestorPrincipal) {
            return qrestorPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        }
        throw new RuntimeException("Principal is not QrestorPrincipal");
    }
}

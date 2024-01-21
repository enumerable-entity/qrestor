package com.qrestor.auth.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtils {

    public String getPrincipalUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof QrestorPrincipal qrestorPrincipal) {
            return qrestorPrincipal.getName();
        }
        return "";
    }
}

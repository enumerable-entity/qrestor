package com.qrestor.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum SystemRole implements GrantedAuthority {

    ADMIN("ADMIN"),
    RESTAURATEUR("RESTAURATEUR"),
    WAITER("WAITER");

    private final String PREFIX = "ROLE_";
    private final String authority;

    SystemRole(String authority) {
        this.authority = PREFIX + authority;
    }

}

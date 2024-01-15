package com.qrestor.auth.config.security.enums;

import lombok.Getter;
//import org.springframework.security.core.GrantedAuthority;

@Getter
public enum SystemRole {//implements GrantedAuthority {

    ADMIN("ROLE_ADMIN"),
    RESTAURATEUR("ROLE_RESTAURATEUR"),
    WAITER("ROLE_WAITER");

    private final String authority;

    SystemRole(String authority) {
        this.authority = authority;
    }

}

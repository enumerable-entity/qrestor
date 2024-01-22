package com.qrestor.commons.security;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.UUID;

@Data
@Builder
public class QrestorPrincipal implements Principal, UserDetails {
    private final String name;
    private final String username;
    private final UUID uuid;
    private final Collection<? extends GrantedAuthority> authorities;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
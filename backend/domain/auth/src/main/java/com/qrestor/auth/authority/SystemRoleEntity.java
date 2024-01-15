package com.qrestor.auth.authority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity
@Table(name = "ROLE")
public class SystemRoleEntity implements GrantedAuthority {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "authority", nullable = false, length = 20)
    private String authority;

}
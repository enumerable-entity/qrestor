package com.qrestor.auth.user.entity;

import com.qrestor.auth.authority.SystemRoleEntity;
import com.qrestor.auth.token.entity.TokenEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "SYSTEM_USERS", indexes = {
        @Index(name = "IDX_SYSTEM_USERS_USERNAME", columnList = "USERNAME", unique = true),
        @Index(name = "IDX_SYSTEM_USERS_EMAIL", columnList = "EMAIL", unique = true)
})
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ACCOUNT_NON_EXPIRED", nullable = false)
    private boolean accountNonExpired;

    @Column(name = "ACCOUNT_NON_LOCKED", nullable = false)
    private boolean accountNonLocked;

    @Column(name = "CREDENTIALS_NON_EXPIRED", nullable = false)
    private boolean credentialsNonExpired;

    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "SYSTEM_USER_TO_ROLE",
            joinColumns = @JoinColumn(name = "SYSTEM_USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    )
    private Collection<SystemRoleEntity> authorities;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<TokenEntity> tokens;

}

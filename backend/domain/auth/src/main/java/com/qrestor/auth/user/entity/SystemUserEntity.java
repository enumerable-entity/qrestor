package com.qrestor.auth.user.entity;

import com.qrestor.auth.authority.SystemRoleEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Table(name = "system_users", schema = "auth", indexes = {
        @Index(name = "IDX_SYSTEM_USERS_USERNAME", columnList = "USERNAME", unique = true),
        @Index(name = "IDX_SYSTEM_USERS_EMAIL", columnList = "EMAIL", unique = true),
        @Index(name = "IDX_SYSTEM_USERS_UUID", columnList = "UUID", unique = true)
})
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @GeneratedValue
    @Builder.Default
    @Column(name = "UUID", nullable = false, unique = true, updatable = false)
    private UUID uuid = UUID.randomUUID();

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "SYSTEM_USER_TO_ROLE",
            joinColumns = @JoinColumn(name = "SYSTEM_USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    )
    private Collection<SystemRoleEntity> authorities;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    @JoinColumn(name = "INFORMATION_ID", referencedColumnName = "ID")
    private SystemUserInformationEntity information;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    @JoinColumn(name = "ADDRESS_ID", referencedColumnName = "ID")
    private AddressEntity address;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    @JoinColumn(name = "SETTINGS_ID", referencedColumnName = "ID")
    private SystemUserSettings settings;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        SystemUserEntity that = (SystemUserEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "uuid = " + uuid + ", " +
                "username = " + username + ", " +
                "email = " + email + ", " +
                "accountNonExpired = " + accountNonExpired + ", " +
                "accountNonLocked = " + accountNonLocked + ", " +
                "credentialsNonExpired = " + credentialsNonExpired + ", " +
                "enabled = " + enabled + ")";
    }
}

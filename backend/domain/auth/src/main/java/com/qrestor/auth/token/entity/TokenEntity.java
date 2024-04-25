package com.qrestor.auth.token.entity;

import com.qrestor.auth.token.enums.TokenType;
import com.qrestor.auth.user.entity.SystemUserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "TOKENS", schema = "AUTH")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "token", nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType type;

    @Column(name = "token_value", nullable = false)
    private String value;

    @Column(name = "user_id", nullable = false, updatable = false, insertable = false)
    private Long userId;

    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SystemUserEntity user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TokenEntity that = (TokenEntity) o;
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
               "value = " + value + ")";
    }
}
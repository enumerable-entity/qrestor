package com.qrestor.auth.token.entity;

import com.qrestor.auth.config.security.enums.TokenType;
import com.qrestor.auth.user.entity.SystemUserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TOKENS")
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

    @Column(name = "value", nullable = false)
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

}
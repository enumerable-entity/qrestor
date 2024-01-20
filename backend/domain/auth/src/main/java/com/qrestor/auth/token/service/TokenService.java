package com.qrestor.auth.token.service;

import com.qrestor.auth.token.enums.TokenType;
import com.qrestor.auth.token.entity.TokenEntity;
import com.qrestor.auth.token.repository.TokenRepository;
import com.qrestor.auth.user.entity.SystemUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;


    public TokenEntity save(TokenEntity token) {
        return tokenRepository.save(token);
    }


    public TokenEntity getNewTokenFor(SystemUserEntity user, TokenType tokenType) {
        return tokenRepository.save(TokenEntity.builder()
                .value(generateToken())
                .type(tokenType)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(1))
                .build());
    }


    private String generateToken() {

        UUID uuid = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        return uuid + ":" + uuid2;
    }

    public Optional<TokenEntity> getToken(String emailConfirmationToken, TokenType tokenType) {
        return tokenRepository.findByValueAndType(emailConfirmationToken, tokenType);
    }
}

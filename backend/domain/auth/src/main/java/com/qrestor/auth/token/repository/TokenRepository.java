package com.qrestor.auth.token.repository;

import com.qrestor.auth.token.enums.TokenType;
import com.qrestor.auth.token.entity.TokenEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {


    @EntityGraph(attributePaths = {"user"})
    Optional<TokenEntity> findByValueAndType(String emailConfirmationToken, TokenType emailVerification);
}
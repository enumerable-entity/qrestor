package com.qrestor.auth.security.jwt;

import com.qrestor.auth.user.entity.SystemUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${app.auth.jwt.secret}")
    private String jwtSecret;

    @Value("${app.auth.jwt.expiration}")
    private long jwtExpiration;

    @Value("${app.auth.jwt.issuer}")
    private String jwtIssuer;


    public String generateToken(SystemUserEntity userDetails) {
        return JwtUtils.generateToken(userDetails, jwtSecret, jwtExpiration, jwtIssuer);
    }
}

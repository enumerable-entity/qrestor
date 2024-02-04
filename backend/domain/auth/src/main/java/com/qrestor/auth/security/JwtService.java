package com.qrestor.auth.security;

import com.qrestor.security.JwtUtils;
import com.qrestor.security.QrestorPrincipal;
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


    public String generateToken(QrestorPrincipal userDetails) {
        return JwtUtils.generateToken(userDetails, jwtSecret, jwtExpiration, jwtIssuer);
    }
}

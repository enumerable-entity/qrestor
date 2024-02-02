package com.qrestor.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@UtilityClass
public class JwtUtils {

    public static final String CLAIM_ROLES = "roles";


    public boolean validateToken(String token, String secretKey) {
        return !isTokenExpired(token, secretKey);
    }

    private boolean isTokenExpired(String token, String secretKey) {
        final Date expiration = getClaim(token, Claims::getExpiration, secretKey);
        return expiration.before(new Date());
    }


    public String generateToken(QrestorPrincipal userDetails, String secretKey,
                                long expirationTime, String issuer) {
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return generateToken(Map.of(CLAIM_ROLES, authorities), userDetails, secretKey, expirationTime, issuer);
    }

    public String generateToken(Map<String, Object> claims, QrestorPrincipal userDetails, String secretKey,
                                long expirationTime, String issuer) {
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(userDetails.getUsername())
                .setId(userDetails.getUuid().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .setIssuer(issuer)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                //.compressWith(new GzipCompressionCodec())
                .compact();
    }


    public UUID getUserUUID(String token, String secretKey) {
        return UUID.fromString(getClaim(token, Claims::getId, secretKey));
    }


    public String getUsername(String token, String secretKey) {
        return getClaim(token, Claims::getSubject, secretKey);
    }

    public List<String> getRoles(String token, String secretKey) {
        Object claim = getClaim(token, CLAIM_ROLES, secretKey);
        return claim instanceof List ? (List<String>) claim : List.of((String) claim);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimName, String secretKey) {
        final Claims claims = getAllClaims(token, secretKey);
        return claimName.apply(claims);
    }

    public Object getClaim(String token, String claimName, String secretKey) {
        final Claims claims = getAllClaims(token, secretKey);
        return claims.get(claimName);
    }


    private Claims getAllClaims(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}

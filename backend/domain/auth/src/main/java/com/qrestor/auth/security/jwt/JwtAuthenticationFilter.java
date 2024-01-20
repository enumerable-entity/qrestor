package com.qrestor.auth.security.jwt;

import com.qrestor.auth.authority.SystemRoleEntity;
import com.qrestor.auth.security.QrestorPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${app.auth.jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        final String jwt;

        if (bearerToken == null || !bearerToken.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = bearerToken.substring(BEARER_PREFIX.length());

        if (JwtUtils.validateToken(jwt, jwtSecret) && SecurityContextHolder.getContext().getAuthentication() == null) {
            final var uuid = JwtUtils.getUserUUID(jwt, jwtSecret);
            final var username = JwtUtils.getUsername(jwt, jwtSecret);
            final var claims = JwtUtils.getRoles(jwt, jwtSecret);
            final var authorities = mapRolesToAuthorities(claims);
            final var authentication = new UsernamePasswordAuthenticationToken(new QrestorPrincipal(username, uuid), null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private List<SystemRoleEntity> mapRolesToAuthorities(List<String> claims) {
        return claims.stream()
                .map(jwtClaim -> SystemRoleEntity.builder()
                        .authority(jwtClaim)
                        .build())
                .toList();
    }
}

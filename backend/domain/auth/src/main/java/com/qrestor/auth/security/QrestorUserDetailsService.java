package com.qrestor.auth.security;

import com.qrestor.auth.user.mapper.UserDetailsMapper;
import com.qrestor.auth.user.service.SystemUserService;
import com.qrestor.security.QrestorPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrestorUserDetailsService implements UserDetailsService {

    private final SystemUserService systemUserService;
    private final UserDetailsMapper userDetailsMapper;

    @Override
    public QrestorPrincipal loadUserByUsername(String email) throws UsernameNotFoundException {
        return systemUserService.findByEmail(email)
                .map(userDetailsMapper::toDto)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

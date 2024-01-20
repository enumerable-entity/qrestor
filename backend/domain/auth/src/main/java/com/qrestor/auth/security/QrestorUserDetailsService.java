package com.qrestor.auth.security;

import com.qrestor.auth.user.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QrestorUserDetailsService implements UserDetailsService {

    private final SystemUserService systemUserService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return systemUserService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("QRestor user not found"));
    }
}

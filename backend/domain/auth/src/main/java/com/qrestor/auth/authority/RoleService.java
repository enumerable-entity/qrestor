package com.qrestor.auth.authority;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;


    public Collection<SystemRoleEntity> findByAuthority(String authority) {
        return roleRepository.findByAuthority(authority);
    }

}

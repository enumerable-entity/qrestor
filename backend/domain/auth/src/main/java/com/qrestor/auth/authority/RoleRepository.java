package com.qrestor.auth.authority;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RoleRepository extends JpaRepository<SystemRoleEntity, Long> {

    @Cacheable(value = "roles", key = "#authority")
    Collection<SystemRoleEntity> findByAuthority(String authority);
}
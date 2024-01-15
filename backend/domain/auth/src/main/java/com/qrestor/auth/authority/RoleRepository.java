package com.qrestor.auth.authority;

import com.qrestor.auth.authority.SystemRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RoleRepository extends JpaRepository<SystemRoleEntity, Long> {

    Collection<SystemRoleEntity> findByAuthority(String authority);
}
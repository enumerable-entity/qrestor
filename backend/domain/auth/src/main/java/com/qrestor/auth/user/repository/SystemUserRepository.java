package com.qrestor.auth.user.repository;

import com.qrestor.auth.user.entity.SystemUserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUserEntity, Long> {
    Optional<SystemUserEntity> findOneByUsername(String username);
    @EntityGraph(attributePaths = {"authorities"})
    Optional<SystemUserEntity> findOneByEmail(String email);

}

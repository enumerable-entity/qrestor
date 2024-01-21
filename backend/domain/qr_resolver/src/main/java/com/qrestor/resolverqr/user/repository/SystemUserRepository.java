package com.qrestor.resolverqr.user.repository;

import com.qrestor.resolverqr.user.enitity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {
}
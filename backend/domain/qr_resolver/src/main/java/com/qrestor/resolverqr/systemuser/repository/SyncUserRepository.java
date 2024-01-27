package com.qrestor.resolverqr.systemuser.repository;

import com.qrestor.resolverqr.systemuser.enitity.SyncUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncUserRepository extends JpaRepository<SyncUser, Long> {
}
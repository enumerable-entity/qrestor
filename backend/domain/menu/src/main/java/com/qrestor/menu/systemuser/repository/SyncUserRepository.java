package com.qrestor.menu.systemuser.repository;

import com.qrestor.menu.systemuser.enitity.SyncUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncUserRepository extends JpaRepository<SyncUser, Long> {
}
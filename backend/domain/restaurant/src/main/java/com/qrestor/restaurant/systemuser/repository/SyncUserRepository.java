package com.qrestor.restaurant.systemuser.repository;

import com.qrestor.restaurant.systemuser.enitity.SyncUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncUserRepository extends JpaRepository<SyncUser, Long> {
}
package com.qrestor.restaurant.systemuser.repository;

import com.qrestor.restaurant.systemuser.enitity.SyncUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SyncUserRepository extends JpaRepository<SyncUser, Long> {
    Optional<SyncUser> getByUuid(UUID uuid);
}
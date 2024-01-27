package com.qrestor.commons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface PublicRepository<E, ID> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {

    Optional<E> findByUuid(UUID uuid);
    Optional<E> findByUuidSecure(UUID uuid, UUID userId);
    void deleteByUuid(UUID id, UUID userId);
}

package com.qrestor.resolverqr.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.resolverqr.entity.QrCodeMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrRepository extends PublicRepository<QrCodeMappingEntity, Long> {
    Optional<QrCodeMappingEntity> findByPublicId(UUID publicId);
}

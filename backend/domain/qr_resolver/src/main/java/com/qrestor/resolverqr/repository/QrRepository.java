package com.qrestor.resolverqr.repository;

import com.qrestor.resolverqr.entity.QrCodeMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QrRepository extends JpaRepository<QrCodeMappingEntity, Long> {
    Optional<QrCodeMappingEntity> findByQrCode(String qrCode);
}

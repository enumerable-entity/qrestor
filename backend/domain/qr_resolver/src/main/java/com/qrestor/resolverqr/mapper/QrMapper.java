package com.qrestor.resolverqr.mapper;

import com.qrestor.models.dto.qr.ResolvingResponseDTO;
import com.qrestor.resolverqr.entity.QrCodeMappingEntity;
import org.mapstruct.Mapper;

@Mapper
public interface QrMapper {
    ResolvingResponseDTO toDto(QrCodeMappingEntity entity);
}

package com.qrestor.resolverqr.mapper;

import com.qrestor.resolverqr.api.dto.ResolvingResponseDTO;
import com.qrestor.resolverqr.entity.QrCodeMappingEntity;
import org.mapstruct.Mapper;

@Mapper
public interface QrMapper {
    ResolvingResponseDTO toDto(QrCodeMappingEntity entity);
}

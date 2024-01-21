package com.qrestor.resolverqr.mapper;

import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.resolverqr.api.dto.QrCodeMappingDTO;
import com.qrestor.resolverqr.entity.QrCodeMappingEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface QrCodesManagementMapper extends CrudMapper<QrCodeMappingDTO, QrCodeMappingEntity> {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "user", ignore = true)
    QrCodeMappingEntity toEntity(QrCodeMappingDTO qrCodeMappingDTO);

    QrCodeMappingDTO toDto(QrCodeMappingEntity qrCodeMappingEntity);


    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    QrCodeMappingEntity partialUpdate(QrCodeMappingDTO dto, @MappingTarget QrCodeMappingEntity entity);

}

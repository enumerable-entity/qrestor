package com.qrestor.commons.mapper;


import com.qrestor.commons.dto.AbstractPublicDTO;
import com.qrestor.commons.entity.PublicEntity;
import org.mapstruct.*;

import java.util.List;


public interface CrudMapper<DTO extends AbstractPublicDTO, ENTITY extends PublicEntity> {

    DTO toDto(ENTITY entity);

    List<DTO> toDto(Iterable<ENTITY> entity);

    ENTITY toEntity(DTO dto);

    List<ENTITY> toEntity(Iterable<DTO> dto);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    ENTITY partialUpdate(DTO dto, @MappingTarget ENTITY entity);
}


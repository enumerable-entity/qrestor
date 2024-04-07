package com.qrestor.sellpoint.mapper;

import com.qrestor.models.dto.auth.AddressDTO;
import com.qrestor.sellpoint.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressMapper {
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "country", ignore = true)
    AddressDTO toDto(AddressEntity entity);

    AddressEntity toEntity(AddressDTO dto);

}

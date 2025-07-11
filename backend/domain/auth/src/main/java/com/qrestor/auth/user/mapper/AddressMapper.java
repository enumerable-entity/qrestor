package com.qrestor.auth.user.mapper;

import com.qrestor.auth.user.entity.AddressEntity;
import com.qrestor.models.dto.auth.AddressDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
    AddressDTO toDto(AddressEntity entity);

    AddressEntity toEntity(AddressDTO dto);

}

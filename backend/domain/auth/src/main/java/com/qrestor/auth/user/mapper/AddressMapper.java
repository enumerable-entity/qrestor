package com.qrestor.auth.user.mapper;

import com.qrestor.auth.user.dto.AddressDTO;
import com.qrestor.auth.user.dto.SystemUserDTO;
import com.qrestor.auth.user.entity.AddressEntity;
import com.qrestor.auth.user.entity.SystemUserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
    AddressDTO toDto(AddressEntity entity);

}

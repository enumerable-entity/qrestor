package com.qrestor.auth.user.mapper;

import com.qrestor.auth.user.dto.UserDescriptorDTO;
import com.qrestor.auth.user.entity.SystemUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserDescriptorMapper {

    @Mapping(target = "user", source = "user")
    UserDescriptorDTO toDto(SystemUserEntity user);

}

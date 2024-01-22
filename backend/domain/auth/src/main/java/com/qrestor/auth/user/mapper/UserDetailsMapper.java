package com.qrestor.auth.user.mapper;

import com.qrestor.auth.user.entity.SystemUserEntity;
import com.qrestor.commons.security.QrestorPrincipal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserDetailsMapper {

    @Mapping(target = "name", source = "username")
    QrestorPrincipal toDto(SystemUserEntity userEntity);
}

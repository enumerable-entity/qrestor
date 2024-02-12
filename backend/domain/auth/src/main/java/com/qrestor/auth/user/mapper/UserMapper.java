package com.qrestor.auth.user.mapper;

import com.qrestor.auth.authority.SystemRoleEntity;
import com.qrestor.models.dto.auth.SystemUserDTO;
import com.qrestor.auth.user.entity.SystemUserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    SystemUserDTO toDto(SystemUserEntity entity);

    SystemUserDTO.RoleDTO toDto(SystemRoleEntity entity);

}

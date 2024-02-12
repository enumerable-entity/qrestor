package com.qrestor.auth.user.mapper;

import com.qrestor.models.dto.auth.UserInformationDTO;
import com.qrestor.auth.user.entity.SystemUserInformationEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserInformationMapper {
    UserInformationDTO toDto(SystemUserInformationEntity entity);
}

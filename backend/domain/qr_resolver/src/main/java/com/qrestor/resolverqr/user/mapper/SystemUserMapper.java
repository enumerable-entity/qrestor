package com.qrestor.resolverqr.user.mapper;

import com.qrestor.commons.kafka.dto.UserKafkaSyncDTO;
import com.qrestor.resolverqr.user.enitity.SystemUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SystemUserMapper {
    @Mapping(target = "id", ignore = true)
    UserKafkaSyncDTO toDto(SystemUser systemUser);
    @Mapping(target = "qrCodes", ignore = true)
    SystemUser toEntity(UserKafkaSyncDTO userKafkaSyncDTO);
}

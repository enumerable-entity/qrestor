package com.qrestor.resolverqr.systemuser.mapper;

import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import com.qrestor.resolverqr.systemuser.enitity.SyncUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SyncUserMapper {
    @Mapping(target = "id", ignore = true)
    UserKafkaSyncDTO toDto(SyncUser syncUser);
    @Mapping(target = "qrCodes", ignore = true)
    SyncUser toEntity(UserKafkaSyncDTO userKafkaSyncDTO);
}

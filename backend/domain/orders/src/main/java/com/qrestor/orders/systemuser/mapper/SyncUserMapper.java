package com.qrestor.orders.systemuser.mapper;

import com.qrestor.commons.kafka.dto.UserKafkaSyncDTO;
import com.qrestor.orders.systemuser.enitity.SyncUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SyncUserMapper {
    @Mapping(target = "id", ignore = true)
    UserKafkaSyncDTO toDto(SyncUser syncUser);
    SyncUser toEntity(UserKafkaSyncDTO userKafkaSyncDTO);
}

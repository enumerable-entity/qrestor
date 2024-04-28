package com.qrestor.menu.systemuser.mapper;

import com.qrestor.menu.systemuser.enitity.SyncUser;
import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SyncUserMapper {
    @Mapping(target = "id", ignore = true)
    UserKafkaSyncDTO toDto(SyncUser syncUser);

    SyncUser toEntity(UserKafkaSyncDTO userKafkaSyncDTO);
}

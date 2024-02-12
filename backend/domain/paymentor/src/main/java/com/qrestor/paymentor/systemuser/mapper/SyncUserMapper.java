package com.qrestor.paymentor.systemuser.mapper;

import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import com.qrestor.paymentor.systemuser.enitity.SyncUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SyncUserMapper {
    @Mapping(target = "id", ignore = true)
    UserKafkaSyncDTO toDto(SyncUser syncUser);
    SyncUser toEntity(UserKafkaSyncDTO userKafkaSyncDTO);
}

package com.qrestor.sellpoint.systemuser.mapper;

import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import com.qrestor.sellpoint.systemuser.enitity.SyncUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SyncUserMapper {
    @Mapping(target = "id", ignore = true)
    UserKafkaSyncDTO toDto(SyncUser syncUser);

    @Mapping(target = "ownedRestaurants", ignore = true)
    @Mapping(target = "sellingPoint", ignore = true)
    SyncUser toEntity(UserKafkaSyncDTO userKafkaSyncDTO);
}

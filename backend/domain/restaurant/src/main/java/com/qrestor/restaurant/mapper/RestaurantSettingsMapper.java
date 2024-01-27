package com.qrestor.restaurant.mapper;

import com.qrestor.commons.mapper.BasicMapper;
import com.qrestor.restaurant.api.dto.RestaurantSettingsDTO;
import com.qrestor.restaurant.entity.RestaurantSettingsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantSettingsMapper extends BasicMapper<RestaurantSettingsDTO, RestaurantSettingsEntity> {
}

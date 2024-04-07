package com.qrestor.sellpoint.mapper;

import com.qrestor.commons.mapper.BasicMapper;
import com.qrestor.sellpoint.api.dto.RestaurantSettingsDTO;
import com.qrestor.sellpoint.entity.SellingPointSettingsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantSettingsMapper extends BasicMapper<RestaurantSettingsDTO, SellingPointSettingsEntity> {
}

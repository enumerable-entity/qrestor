package com.qrestor.restaurant.mapper;

import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.restaurant.api.dto.RestaurantDTO;
import com.qrestor.restaurant.entity.SellingPointEntity;
import com.qrestor.restaurant.entity.SellingPointSettingsEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {AddressMapper.class})
public interface RestaurantMapper extends CrudMapper<RestaurantDTO, SellingPointEntity> {

    @AfterMapping
    default void linkRestaurantSettingsEntity(@MappingTarget SellingPointEntity sellingPointEntity) {
        SellingPointSettingsEntity sellingPointSettingsEntity = sellingPointEntity.getSettings();
        if (sellingPointSettingsEntity != null) {
            sellingPointSettingsEntity.setSellingPoint(sellingPointEntity);
        }
    }
}
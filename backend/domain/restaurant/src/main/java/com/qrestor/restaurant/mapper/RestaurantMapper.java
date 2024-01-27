package com.qrestor.restaurant.mapper;

import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.restaurant.api.dto.RestaurantDTO;
import com.qrestor.restaurant.entity.RestaurantEntity;
import com.qrestor.restaurant.entity.RestaurantSettingsEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper extends CrudMapper<RestaurantDTO, RestaurantEntity> {

    @AfterMapping
    default void linkRestaurantSettingsEntity(@MappingTarget RestaurantEntity restaurantEntity) {
        RestaurantSettingsEntity restaurantSettingsEntity = restaurantEntity.getSettings();
        if (restaurantSettingsEntity != null) {
            restaurantSettingsEntity.setRestaurant(restaurantEntity);
        }
    }
}
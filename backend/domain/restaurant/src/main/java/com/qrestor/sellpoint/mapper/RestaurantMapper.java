package com.qrestor.sellpoint.mapper;

import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.sellpoint.api.dto.RestaurantDTO;
import com.qrestor.sellpoint.entity.SellingPointEntity;
import com.qrestor.sellpoint.entity.SellingPointSettingsEntity;
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
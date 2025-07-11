package com.qrestor.menu.mapper;

import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.menu.entity.MenuItemOptionPositionEntity;
import com.qrestor.models.dto.menu.MenuItemOptionPositionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE)
public interface MenuItemOptionPositionMapper extends CrudMapper<MenuItemOptionPositionDTO, MenuItemOptionPositionEntity> {
    @Mapping(target = "itemOptionId", source = "menuItemOption.publicId")
    @Mapping(target = "itemOptionTitle", source = "menuItemOption.title")
    @Override
    MenuItemOptionPositionDTO toDto(MenuItemOptionPositionEntity entity);

    @Mapping(target = "menuItemOption", ignore = true)
    @Override
    MenuItemOptionPositionEntity toEntity(MenuItemOptionPositionDTO dto);
}
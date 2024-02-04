package com.qrestor.menu.mapper;

import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.models.dto.menu.MenuItemOptionDTO;
import com.qrestor.menu.entity.MenuItemOptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, uses = {MenuItemOptionPositionMapper.class})
public interface MenuItemOptionMapper extends CrudMapper<MenuItemOptionDTO, MenuItemOptionEntity> {
    @Override
    @Mapping(target = "menuItemId", source = "entity.menuItem.publicId")
    MenuItemOptionDTO toDto(MenuItemOptionEntity entity);

}

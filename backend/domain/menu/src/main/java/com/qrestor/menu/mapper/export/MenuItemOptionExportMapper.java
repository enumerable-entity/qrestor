package com.qrestor.menu.mapper.export;

import com.qrestor.menu.entity.MenuItemOptionEntity;
import com.qrestor.models.dto.menu.eximport.MenuItemOptionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, uses = {MenuItemOptionPositionExportMapper.class})
public interface MenuItemOptionExportMapper {
    @Mapping(target = "menuItemId", source = "entity.menuItem.publicId")
    MenuItemOptionDTO toDTO(MenuItemOptionEntity entity);

    Collection<MenuItemOptionDTO> toDTOs(Collection<MenuItemOptionEntity> entity);

}

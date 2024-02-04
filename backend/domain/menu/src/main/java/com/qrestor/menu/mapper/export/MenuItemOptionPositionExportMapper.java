package com.qrestor.menu.mapper.export;

import com.qrestor.menu.entity.MenuItemOptionPositionEntity;
import com.qrestor.models.dto.menu.MenuItemOptionPositionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE)
public interface MenuItemOptionPositionExportMapper {
    @Mapping(target = "itemOptionId", source = "menuItemOption.publicId")
    MenuItemOptionPositionDTO toDTO(MenuItemOptionPositionEntity entity);

    Collection<MenuItemOptionPositionDTO> toDTOs(Collection<MenuItemOptionPositionEntity> entity);
}
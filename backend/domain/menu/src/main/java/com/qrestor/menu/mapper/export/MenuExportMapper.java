package com.qrestor.menu.mapper.export;

import com.qrestor.menu.entity.MenuEntity;
import com.qrestor.models.dto.menu.eximport.MenuDTO;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {MenuItemOptionExportMapper.class})
public interface MenuExportMapper {
    MenuDTO toDTO(MenuEntity entity);

    List<MenuDTO> toDTOs(Collection<MenuEntity> entity);
}

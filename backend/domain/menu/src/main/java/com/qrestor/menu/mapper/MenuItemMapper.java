package com.qrestor.menu.mapper;

import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.menu.api.dto.MenuItemDTO;
import com.qrestor.menu.entity.MenuItemEntity;
import org.mapstruct.*;

@Mapper(uses = {CategoryMapper.class, IngredientMapper.class})
public interface MenuItemMapper extends CrudMapper<MenuItemDTO, MenuItemEntity> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            unmappedTargetPolicy = ReportingPolicy.IGNORE)
    @Mapping(target = "ingredients", ignore = true)
    MenuItemEntity toEntity(MenuItemDTO menuItemDTO);

}

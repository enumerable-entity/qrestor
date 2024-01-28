package com.qrestor.menu.mapper;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.qrestor.menu.api.dto.list.MenuItemListDTO;
import com.qrestor.menu.api.dto.list.MenuListDTO;
import com.qrestor.menu.entity.IngredientEntity;
import com.qrestor.menu.entity.ItemCategoryEntity;
import com.qrestor.menu.entity.MenuEntity;
import com.qrestor.menu.entity.MenuItemEntity;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper
public interface PublicMenuMapper {


    default List<MenuListDTO> toListDTO(MenuEntity entity) {
        Multimap<ItemCategoryEntity, MenuItemEntity> menuListDTOMultimap = MultimapBuilder.hashKeys().arrayListValues().build();
        entity.getMenuItems()
                .forEach(item -> menuListDTOMultimap.put(item.getItemCategory(), item));

        List<MenuListDTO> menuListDTOS = new ArrayList<>();
        menuListDTOMultimap.asMap().forEach((key, value) -> {
            MenuListDTO menuListDTO = new MenuListDTO(
                    key.getNlsKey(),
                    key.getPublicId(),
                    key.getIconCode(),
                    mapItems(value)
            );
            menuListDTOS.add(menuListDTO);
        });
        return menuListDTOS;
    }

    default List<MenuItemListDTO> mapItems(Collection<MenuItemEntity> items) {
        return items.stream().map(item ->
                new MenuItemListDTO(
                        item.getPublicId(),
                        item.getItemCategory().getPublicId(),
                        item.getTitle(),
                        item.getDescription(),
                        item.getImageUrl(),
                        item.getPrice(),
                        mapIngredients(item.getIngredients())
                )
        ).toList();
    }

    default List<String> mapIngredients(Set<IngredientEntity> ingredients) {
        return ingredients.stream().map(IngredientEntity::getName).toList();
    }
}

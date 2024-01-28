package com.qrestor.menu.mapper;

import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.menu.api.dto.ItemCategoryDTO;
import com.qrestor.menu.entity.ItemCategoryEntity;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper extends CrudMapper<ItemCategoryDTO, ItemCategoryEntity> {
}
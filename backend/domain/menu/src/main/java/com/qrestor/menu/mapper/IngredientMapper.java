package com.qrestor.menu.mapper;

import com.qrestor.commons.mapper.CrudMapper;
import com.qrestor.menu.api.dto.IngredientDTO;
import com.qrestor.menu.entity.IngredientEntity;
import org.mapstruct.Mapper;

@Mapper
public interface IngredientMapper extends CrudMapper<IngredientDTO, IngredientEntity> {
}
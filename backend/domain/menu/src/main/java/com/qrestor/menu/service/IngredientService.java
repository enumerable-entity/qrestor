package com.qrestor.menu.service;

import com.qrestor.commons.CrudService;
import com.qrestor.menu.api.dto.IngredientDTO;
import com.qrestor.menu.entity.IngredientEntity;

import java.util.List;
import java.util.UUID;

public interface IngredientService extends CrudService<IngredientDTO> {
    List<IngredientEntity> findEntityByUuidIn(List<UUID> uuids);
}

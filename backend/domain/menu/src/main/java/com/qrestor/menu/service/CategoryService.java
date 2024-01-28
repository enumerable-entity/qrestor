package com.qrestor.menu.service;

import com.qrestor.commons.CrudService;
import com.qrestor.menu.api.dto.ItemCategoryDTO;
import com.qrestor.menu.entity.ItemCategoryEntity;

import java.util.Optional;
import java.util.UUID;

public interface CategoryService extends CrudService<ItemCategoryDTO> {
    Optional<ItemCategoryEntity> findByPublicId(UUID publicId);
}

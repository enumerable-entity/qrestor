package com.qrestor.menu.service.impl;

import com.qrestor.commons.AbstractCrudService;
import com.qrestor.menu.api.dto.ItemCategoryDTO;
import com.qrestor.menu.entity.ItemCategoryEntity;
import com.qrestor.menu.mapper.CategoryMapper;
import com.qrestor.menu.repository.CategoryRepository;
import com.qrestor.menu.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * Service for {@link com.qrestor.menu.entity.ItemCategoryEntity}
 * Categories are predefined and cannot be created by the user
 */
@Service
public class CategoryServiceImpl extends AbstractCrudService<ItemCategoryDTO, ItemCategoryEntity> implements CategoryService {
    public CategoryServiceImpl(CategoryMapper mapper, CategoryRepository repository) {
        super(mapper, repository);
    }
}

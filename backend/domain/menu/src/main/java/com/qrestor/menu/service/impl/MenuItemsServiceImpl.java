package com.qrestor.menu.service.impl;

import com.qrestor.commons.AbstractCrudService;
import com.qrestor.commons.security.SecurityUtils;
import com.qrestor.menu.api.dto.IngredientDTO;
import com.qrestor.menu.api.dto.MenuItemDTO;
import com.qrestor.menu.entity.IngredientEntity;
import com.qrestor.menu.entity.ItemCategoryEntity;
import com.qrestor.menu.entity.MenuItemEntity;
import com.qrestor.menu.mapper.MenuItemMapper;
import com.qrestor.menu.repository.MenuItemsRepository;
import com.qrestor.menu.service.CategoryService;
import com.qrestor.menu.service.IngredientService;
import com.qrestor.menu.service.MenuItemsService;
import com.qrestor.menu.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemsServiceImpl extends AbstractCrudService<MenuItemDTO, MenuItemEntity> implements MenuItemsService {

    private final IngredientService ingredientService;
    private final CategoryService categoryService;
    private final MenuService menuService;

    public MenuItemsServiceImpl(MenuItemMapper mapper, MenuItemsRepository repository, IngredientService ingredientService,
                                CategoryService categoryService, MenuService menuService) {
        super(mapper, repository);
        this.ingredientService = ingredientService;
        this.categoryService = categoryService;
        this.menuService = menuService;
    }

    @Override
    @Transactional
    public MenuItemDTO create(MenuItemDTO dto) {
        dto.setPublicId(generateQrCode());
        MenuItemEntity entity = mapper.toEntity(dto);
        Optional<ItemCategoryEntity> category = categoryService.findByPublicId(dto.getItemCategory().getPublicId());
        entity.setItemCategory(category.orElseThrow(() -> new RuntimeException("Category not found")));
        if (dto.getMenu() != null && dto.getMenu().getPublicId() != null)
            entity.setMenu(menuService.findEntityByUuid(dto.getMenu().getPublicId()).orElseThrow(
                    () -> new RuntimeException("Menu not found")));
        List<IngredientEntity> ingredientEntities =
                ingredientService.findEntityByUuidIn(dto.getIngredients().stream().map(IngredientDTO::getPublicId).toList());
        entity.getIngredients().addAll(ingredientEntities);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    @Transactional
    public MenuItemDTO update(MenuItemDTO dto) {
        MenuItemEntity existingEntity = repository.findByUuidSecure(dto.getPublicId(), SecurityUtils.getPrincipalUUID())
                .orElseThrow(() -> new RuntimeException("Entity not found"));

        MenuItemEntity updatedEntity = mapper.partialUpdate(dto, existingEntity);
        Optional<ItemCategoryEntity> category = categoryService.findByPublicId(dto.getItemCategory().getPublicId());
        updatedEntity.setItemCategory(category.orElseThrow(() -> new RuntimeException("Category not found")));
        if (dto.getMenu() != null && dto.getMenu().getPublicId() != null)
            updatedEntity.setMenu(menuService.findEntityByUuid(dto.getMenu().getPublicId()).orElseThrow(
                    () -> new RuntimeException("Menu not found")));
        List<IngredientEntity> ingredientEntities =
                ingredientService.findEntityByUuidIn(dto.getIngredients().stream().map(IngredientDTO::getPublicId).toList());
        updatedEntity.getIngredients().clear();
        updatedEntity.getIngredients().addAll(ingredientEntities);
        return mapper.toDto(repository.save(updatedEntity));
    }
}

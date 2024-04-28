package com.qrestor.menu.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qrestor.commons.AbstractCrudService;
import com.qrestor.commons.Utils;
import com.qrestor.menu.api.dto.IngredientDTO;
import com.qrestor.menu.api.dto.MenuItemDTO;
import com.qrestor.menu.entity.*;
import com.qrestor.menu.mapper.MenuItemMapper;
import com.qrestor.menu.mapper.PublicMenuMapper;
import com.qrestor.menu.repository.MenuItemsRepository;
import com.qrestor.menu.repository.MenuRepository;
import com.qrestor.menu.repository.mongo.AggregateMenuRepository;
import com.qrestor.menu.service.CategoryService;
import com.qrestor.menu.service.IngredientService;
import com.qrestor.menu.service.MenuItemsService;
import com.qrestor.menu.service.MenuService;
import com.qrestor.models.Pair;
import com.qrestor.security.SecurityUtils;
import lombok.SneakyThrows;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuItemsServiceImpl extends AbstractCrudService<MenuItemDTO, MenuItemEntity> implements MenuItemsService {

    private final IngredientService ingredientService;
    private final CategoryService categoryService;
    private final MenuService menuService;
    private final AggregateMenuRepository aggregateMenuRepository;
    private final PublicMenuMapper publicMenuMapper;

    public MenuItemsServiceImpl(MenuItemMapper mapper,
                                MenuItemsRepository repository,
                                IngredientService ingredientService,
                                CategoryService categoryService,
                                MenuService menuService,
                                AggregateMenuRepository aggregateMenuRepository,
                                PublicMenuMapper publicMenuMapper) {
        super(mapper, repository);
        this.ingredientService = ingredientService;
        this.categoryService = categoryService;
        this.menuService = menuService;
        this.aggregateMenuRepository = aggregateMenuRepository;
        this.publicMenuMapper = publicMenuMapper;
    }

    @Override
    @Transactional
    public MenuItemDTO create(MenuItemDTO dto) {
        dto.setPublicId(Utils.generatePublicId());
        MenuItemEntity entity = mapper.toEntity(dto);
        Optional<ItemCategoryEntity> category = categoryService.findByPublicId(dto.getItemCategory().getPublicId());
        entity.setItemCategory(category.orElseThrow(() -> new RuntimeException("Category not found")));
        if (dto.getMenu() != null && dto.getMenu().getPublicId() != null) {
            MenuEntity menuRef = menuService.findEntityByUuid(dto.getMenu().getPublicId()).orElseThrow(
                    () -> new RuntimeException("Menu not found"));
            entity.setMenu(menuRef);
            menuRef.getMenuItems().add(entity);
        }
        List<IngredientEntity> ingredientEntities =
                ingredientService.findEntityByUuidIn(dto.getIngredients().stream().map(IngredientDTO::getPublicId).toList());
        entity.getIngredients().addAll(ingredientEntities);
        MenuItemEntity save = repository.save(entity);
        MenuItemDTO dto1 = mapper.toDto(save);
        saveDocument(save.getMenu());
        return dto1;
    }

    @SneakyThrows
    private void saveDocument(MenuEntity menuRef) {
        aggregateMenuRepository.save(new AggregatedMenuDocumentEntity(menuRef.getRestaurantId(), publicMenuMapper.toAggregateObject(menuRef)));
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

    @Override
    public Optional<MenuItemEntity> findEntityByUuidIn(UUID menuItemId) {
        return repository.findByUuidSecure(menuItemId, SecurityUtils.getPrincipalUUID());
    }


    @Override
    public Map<UUID, Pair<String, Long>> getMenuItemsPriceMap(Set<UUID> menuItemIds) {
        List<MenuItemEntity> byPublicIdIn = ((MenuItemsRepository) repository).findByPublicIdIn(menuItemIds);
        return byPublicIdIn.stream().collect(Collectors.toMap(
                MenuItemEntity::getPublicId, menuItemProj -> new Pair<>(menuItemProj.getTitle(), menuItemProj.getPrice())));

    }

    @Override
    public List<MenuItemDTO> findAllByMenuId(Pageable pageable,
                                             UUID menuId,
                                             boolean publicRequest) {
        Specification<MenuItemEntity> spec;
        if (menuId != null) {
            spec = Specification.where((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(MenuItemEntity.Fields.menu)
                            .get(MenuEntity.Fields.publicId), menuId));
        } else spec = Specification.where(null);
        if (!publicRequest) spec.and(OWNER_SPEC);
        return mapper.toDto(repository.findAll(spec, pageable));
    }
}

package com.qrestor.menu.service.impl;

import com.qrestor.commons.AbstractCrudService;
import com.qrestor.commons.Utils;
import com.qrestor.menu.entity.MenuItemOptionEntity;
import com.qrestor.menu.entity.MenuItemOptionPositionEntity;
import com.qrestor.menu.mapper.MenuItemOptionPositionMapper;
import com.qrestor.menu.repository.MenuItemOptionPositionsRepository;
import com.qrestor.menu.service.MenuItemOptionPositionsService;
import com.qrestor.menu.service.MenuItemOptionsService;
import com.qrestor.models.Pair;
import com.qrestor.models.dto.menu.MenuItemOptionPositionDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuItemOptionPositionsServiceImpl extends
        AbstractCrudService<MenuItemOptionPositionDTO, MenuItemOptionPositionEntity> implements MenuItemOptionPositionsService {

    private final MenuItemOptionsService menuItemOptionsService;

    public MenuItemOptionPositionsServiceImpl(MenuItemOptionPositionMapper mapper,
                                              MenuItemOptionPositionsRepository repository,
                                              MenuItemOptionsService menuItemOptionsService) {
        super(mapper, repository);
        this.menuItemOptionsService = menuItemOptionsService;
    }

    @Override
    public MenuItemOptionPositionDTO create(MenuItemOptionPositionDTO dto) {
        dto.setPublicId(Utils.generatePublicId());
        MenuItemOptionPositionEntity entity = mapper.toEntity(dto);
        Optional<MenuItemOptionEntity> menuOption = menuItemOptionsService.findEntityByUuidIn(dto.getItemOptionId());
        entity.setMenuItemOption(menuOption.orElseThrow(() -> new RuntimeException("Menu option not found")));
        return mapper.toDto(repository.save(entity));
    }


    @Override
    public Map<UUID, Pair<String, Long>> getMenuItemsOptionsPositionsPriceMap(Set<UUID> menuItemOptionsIds) {
        List<MenuItemOptionPositionEntity> byPublicIdIn = ((MenuItemOptionPositionsRepository) repository).findByPublicIdIn(menuItemOptionsIds);
        return byPublicIdIn.stream().collect(Collectors.toMap(
                MenuItemOptionPositionEntity::getPublicId, menuItemProj -> new Pair<>(menuItemProj.getTitle(), menuItemProj.getPrice())));
    }

    @Override
    public List<MenuItemOptionPositionDTO> findAllByOptionId(Pageable pageable,
                                                             UUID optionId,
                                                             boolean publicRequest) {
        Specification<MenuItemOptionPositionEntity> spec;
        if (optionId != null) {
            spec = Specification.where((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(MenuItemOptionPositionEntity.Fields.menuItemOption)
                            .get(MenuItemOptionEntity.Fields.publicId), optionId));
        } else spec = Specification.where(null);
        if (!publicRequest) spec.and(OWNER_SPEC);
        return mapper.toDto(repository.findAll(spec, pageable));
    }
}

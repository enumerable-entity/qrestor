package com.qrestor.menu.service.impl;

import com.qrestor.commons.AbstractCrudService;
import com.qrestor.commons.Utils;
import com.qrestor.menu.entity.MenuItemEntity;
import com.qrestor.menu.entity.MenuItemOptionEntity;
import com.qrestor.menu.mapper.MenuItemOptionMapper;
import com.qrestor.menu.repository.MenuItemOptionsRepository;
import com.qrestor.menu.service.MenuItemOptionsService;
import com.qrestor.menu.service.MenuItemsService;
import com.qrestor.models.dto.menu.MenuItemOptionDTO;
import com.qrestor.security.SecurityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuItemOptionsServiceImpl extends AbstractCrudService<MenuItemOptionDTO, MenuItemOptionEntity> implements MenuItemOptionsService {
    private final MenuItemsService menuItemsService;

    public MenuItemOptionsServiceImpl(MenuItemOptionMapper mapper, MenuItemOptionsRepository repository,
                                      MenuItemsService menuItemsService) {
        super(mapper, repository);
        this.menuItemsService = menuItemsService;
    }

    @Override
    public Optional<MenuItemOptionEntity> findEntityByUuidIn(UUID uuid) {
        return repository.findByUuidSecure(uuid, SecurityUtils.getPrincipalUUID());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItemOptionDTO> findAllByMenuItemId(Pageable pageable, UUID menuItemId, boolean publicRequest) {
        Specification<MenuItemOptionEntity> spec;
        if (menuItemId != null) {
            spec = Specification.where((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(MenuItemOptionEntity.Fields.menuItem)
                            .get(MenuItemEntity.Fields.publicId), menuItemId));
        } else spec = Specification.where(null);
        if (!publicRequest) spec.and(OWNER_SPEC);
        return mapper.toDto(repository.findAll(spec, pageable));
    }

    @Override
    public MenuItemOptionDTO create(MenuItemOptionDTO dto) {
        dto.setPublicId(Utils.generatePublicId());
        MenuItemOptionEntity entity = mapper.toEntity(dto);
        Optional<MenuItemEntity> menuOption = menuItemsService.findEntityByUuidIn(dto.getMenuItemId());
        entity.setMenuItem(menuOption.orElseThrow(() -> new RuntimeException("Menu option not found")));
        return mapper.toDto(repository.save(entity));
    }
}

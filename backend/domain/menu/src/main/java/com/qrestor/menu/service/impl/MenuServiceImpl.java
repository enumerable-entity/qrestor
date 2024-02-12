package com.qrestor.menu.service.impl;

import com.qrestor.commons.AbstractCrudService;
import com.qrestor.menu.mapper.export.MenuExportMapper;
import com.qrestor.models.Pair;
import com.qrestor.models.dto.menu.eximport.MenuAgregateDTO;
import com.qrestor.security.SecurityUtils;
import com.qrestor.menu.api.dto.MenuDTO;
import com.qrestor.menu.entity.MenuEntity;
import com.qrestor.menu.mapper.MenuMapper;
import com.qrestor.menu.repository.MenuRepository;
import com.qrestor.menu.service.MenuService;
import com.qrestor.menu.validator.MenuValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MenuServiceImpl extends AbstractCrudService<MenuDTO, MenuEntity> implements MenuService {
    private final MenuValidator validator;
    private final MenuExportMapper exportMapper;

    public MenuServiceImpl(MenuMapper mapper, MenuRepository repository,
                           MenuValidator validator, MenuExportMapper exportMapper) {
        super(mapper, repository);
        this.validator = validator;
        this.exportMapper = exportMapper;
    }

    @Override
    public MenuDTO create(MenuDTO dto) {
        validator.validateAdd(dto);
        return super.create(dto);
    }

    @Override
    public Optional<MenuEntity> findEntityByUuid(UUID uuid) {
        return repository.findByUuidSecure(uuid, SecurityUtils.getPrincipalUUID());
    }

    @Override
    @Transactional(readOnly = true)
    public MenuAgregateDTO getMenuAggregateForRestaurant(UUID restaurantId) {
        Collection<MenuEntity> allRestaurantMenus = ((MenuRepository) repository).findAllByUserIdAndRestaurantId(SecurityUtils.getPrincipalUUID(), restaurantId);
        if (!allRestaurantMenus.isEmpty()) {
            MenuAgregateDTO menuAgregateDTO = new MenuAgregateDTO();
            menuAgregateDTO.setMenus(exportMapper.toDTOs(allRestaurantMenus));
            return menuAgregateDTO;
        }
        return new MenuAgregateDTO();
    }
}

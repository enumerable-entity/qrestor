package com.qrestor.menu.service.impl;

import com.qrestor.commons.AbstractCrudService;
import com.qrestor.commons.security.SecurityUtils;
import com.qrestor.menu.api.dto.MenuDTO;
import com.qrestor.menu.entity.MenuEntity;
import com.qrestor.menu.mapper.MenuMapper;
import com.qrestor.menu.repository.MenuRepository;
import com.qrestor.menu.service.MenuService;
import com.qrestor.menu.validator.MenuValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MenuServiceImpl extends AbstractCrudService<MenuDTO, MenuEntity> implements MenuService {
    private final MenuValidator validator;

    public MenuServiceImpl(MenuMapper mapper, MenuRepository repository, MenuValidator validator) {
        super(mapper, repository);
        this.validator = validator;
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
}

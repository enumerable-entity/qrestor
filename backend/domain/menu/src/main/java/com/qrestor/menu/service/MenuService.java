package com.qrestor.menu.service;

import com.qrestor.commons.CrudService;
import com.qrestor.menu.api.dto.MenuDTO;
import com.qrestor.menu.entity.MenuEntity;

import java.util.Optional;
import java.util.UUID;

public interface MenuService extends CrudService<MenuDTO> {
    Optional<MenuEntity> findEntityByUuid(UUID uuid);
}

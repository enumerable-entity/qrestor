package com.qrestor.menu.service;

import com.qrestor.commons.CrudService;
import com.qrestor.menu.api.dto.MenuItemDTO;
import com.qrestor.menu.entity.MenuItemEntity;

import java.util.Optional;
import java.util.UUID;

public interface MenuItemsService extends CrudService<MenuItemDTO> {
    Optional<MenuItemEntity> findEntityByUuidIn(UUID menuItemId);
}

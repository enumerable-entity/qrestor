package com.qrestor.menu.service;

import com.qrestor.commons.CrudService;
import com.qrestor.commons.menu.dto.MenuItemOptionDTO;
import com.qrestor.menu.entity.MenuItemOptionEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemOptionsService extends CrudService<MenuItemOptionDTO> {
    Optional<MenuItemOptionEntity> findEntityByUuidIn(UUID uuid);

    List<MenuItemOptionDTO> findAllByMenuItemId(Pageable pageable, UUID menuItemId);
}

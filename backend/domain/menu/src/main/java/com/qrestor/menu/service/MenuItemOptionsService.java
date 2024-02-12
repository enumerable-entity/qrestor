package com.qrestor.menu.service;

import com.qrestor.commons.CrudService;
import com.qrestor.menu.entity.MenuItemOptionEntity;
import com.qrestor.models.Pair;
import com.qrestor.models.dto.menu.MenuItemOptionDTO;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface MenuItemOptionsService extends CrudService<MenuItemOptionDTO> {
    Optional<MenuItemOptionEntity> findEntityByUuidIn(UUID uuid);

    List<MenuItemOptionDTO> findAllByMenuItemId(Pageable pageable, UUID menuItemId, boolean publicRequest);

}

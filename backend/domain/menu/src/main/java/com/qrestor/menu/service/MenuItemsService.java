package com.qrestor.menu.service;

import com.qrestor.commons.CrudService;
import com.qrestor.menu.api.dto.MenuItemDTO;
import com.qrestor.menu.entity.MenuItemEntity;
import com.qrestor.models.Pair;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface MenuItemsService extends CrudService<MenuItemDTO> {
    Optional<MenuItemEntity> findEntityByUuidIn(UUID menuItemId);

    Map<UUID, Pair<String, Long>> getMenuItemsPriceMap(Set<UUID> menuItemIds);

    List<MenuItemDTO> findAllByMenuId(Pageable pageable,
                                      UUID menuId,
                                      boolean publicRequest);
}

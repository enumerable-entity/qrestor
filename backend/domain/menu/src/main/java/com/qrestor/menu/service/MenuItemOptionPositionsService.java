package com.qrestor.menu.service;

import com.qrestor.commons.CrudService;
import com.qrestor.models.Pair;
import com.qrestor.models.dto.menu.MenuItemOptionPositionDTO;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface MenuItemOptionPositionsService extends CrudService<MenuItemOptionPositionDTO> {

    Map<UUID, Pair<String, Long>> getMenuItemsOptionsPositionsPriceMap(Set<UUID> menuItemsOptionsPositionsUUIDs);
}

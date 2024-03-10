package com.qrestor.menu.service;

import com.qrestor.commons.CrudService;
import com.qrestor.models.Pair;
import com.qrestor.models.dto.menu.MenuItemOptionPositionDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface MenuItemOptionPositionsService extends CrudService<MenuItemOptionPositionDTO> {

    Map<UUID, Pair<String, Long>> getMenuItemsOptionsPositionsPriceMap(Set<UUID> menuItemsOptionsPositionsUUIDs);

    public List<MenuItemOptionPositionDTO> findAllByOptionId(Pageable pageable, UUID optionId, boolean b);
}

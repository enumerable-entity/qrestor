package com.qrestor.menu.service;

import com.qrestor.commons.menu.dto.MenuItemOptionDTO;
import com.qrestor.menu.api.dto.list.MenuListDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface MenuPublicService {
    List<MenuListDTO> getActiveMenu(UUID restaurantId);
    List<MenuItemOptionDTO> getMenuItemOptions(UUID menuId, UUID menuItemId);
}

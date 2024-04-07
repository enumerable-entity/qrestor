package com.qrestor.menu.service;

import com.qrestor.menu.api.dto.list.MenuListDTO;
import com.qrestor.models.dto.menu.MenuItemOptionDTO;

import java.util.List;
import java.util.UUID;

public interface MenuPublicService {
    List<MenuListDTO> getActiveMenu(UUID restaurantId);

    List<MenuItemOptionDTO> getMenuItemOptions(UUID menuId,
                                               UUID menuItemId);
}

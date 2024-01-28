package com.qrestor.menu.service;

import com.qrestor.menu.api.dto.list.MenuListDTO;

import java.util.List;
import java.util.UUID;

public interface MenuPublicService {
    List<MenuListDTO> getActiveMenu(UUID restaurantId);
}

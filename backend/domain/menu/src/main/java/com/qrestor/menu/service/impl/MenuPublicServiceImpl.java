package com.qrestor.menu.service.impl;

import com.qrestor.menu.api.dto.list.MenuListDTO;
import com.qrestor.menu.entity.MenuEntity;
import com.qrestor.menu.mapper.PublicMenuMapper;
import com.qrestor.menu.repository.MenuRepository;
import com.qrestor.menu.service.MenuItemOptionsService;
import com.qrestor.menu.service.MenuPublicService;
import com.qrestor.models.dto.menu.MenuItemOptionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuPublicServiceImpl implements MenuPublicService {

    private final MenuRepository repository;
    private final PublicMenuMapper mapper;
    private final MenuItemOptionsService menuItemOptionsService;

    @Override
    public List<MenuListDTO> getActiveMenu(UUID restaurantId) {
        Optional<MenuEntity> activeMenu = repository.findActiveMenuEntityByRestaurantIdAndIsActiveTrue(restaurantId);
        return activeMenu.map(mapper::toListDTO).orElseThrow(
                () -> new RuntimeException("No active menu found for restaurant " + restaurantId)
        );
    }

    @Override
    public List<MenuItemOptionDTO> getMenuItemOptions(UUID menuItemId) {
        return menuItemOptionsService.findAllByMenuItemId(PageRequest.of(0, 99999), menuItemId, true);
    }

}

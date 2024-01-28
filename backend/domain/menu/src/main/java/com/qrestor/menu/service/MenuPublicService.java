package com.qrestor.menu.service;

import com.qrestor.menu.api.dto.list.MenuListDTO;
import com.qrestor.menu.entity.MenuEntity;
import com.qrestor.menu.mapper.PublicMenuMapper;
import com.qrestor.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuPublicService {

    private final MenuRepository repository;
    private final PublicMenuMapper mapper;

    public List<MenuListDTO> getActiveMenu(UUID restaurantId) {
        Optional<MenuEntity> activeMenu = repository.findActiveMenuEntityByRestaurantIdAndIsActiveTrue(restaurantId);
        return activeMenu.map(mapper::toListDTO).orElseThrow(
                () -> new RuntimeException("No active menu found for restaurant " + restaurantId)
        );
    }

}

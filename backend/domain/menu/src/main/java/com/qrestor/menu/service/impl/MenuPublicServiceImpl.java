package com.qrestor.menu.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qrestor.menu.api.RestApiException;
import com.qrestor.menu.api.dto.list.MenuListDTO;
import com.qrestor.menu.entity.AggregatedMenuDocumentEntity;
import com.qrestor.menu.mapper.PublicMenuMapper;
import com.qrestor.menu.repository.MenuRepository;
import com.qrestor.menu.repository.mongo.AggregateMenuRepository;
import com.qrestor.menu.service.MenuItemOptionsService;
import com.qrestor.menu.service.MenuPublicService;
import com.qrestor.models.dto.menu.MenuItemOptionDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuPublicServiceImpl implements MenuPublicService {

    private final MenuRepository repository;
    private final AggregateMenuRepository mongoDocumentsRepository;
    private final PublicMenuMapper mapper;
    private final MenuItemOptionsService menuItemOptionsService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public List<MenuListDTO> getActiveMenu(UUID restaurantId) {
        return mongoDocumentsRepository.findById(restaurantId)
                .map(AggregatedMenuDocumentEntity::getMenuAggregate)
                .orElseThrow(() -> new RestApiException("No active menu found for restaurant " + restaurantId));
    }

    @Override
    public List<MenuItemOptionDTO> getMenuItemOptions(UUID menuItemId) {
        return menuItemOptionsService.findAllByMenuItemId(PageRequest.of(0, 99999), menuItemId, true);
    }

}

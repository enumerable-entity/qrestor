package com.qrestor.menu.api.controller;

import com.qrestor.menu.service.MenuService;
import com.qrestor.models.dto.menu.eximport.MenuAgregateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.qrestor.menu.api.RestEndpoints.INTEGRATION;

@RestController
@RequestMapping(INTEGRATION)
@RequiredArgsConstructor
@PreAuthorize("hasRole('RESTAURATEUR')")
public class IntegrationController {

    private final MenuService menuService;

    @RequestMapping("/restaurant/{restaurantId}/menus")
    public ResponseEntity<MenuAgregateDTO> getRestaurantMenusAggregate(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(menuService.getMenuAggregateForRestaurant(restaurantId));
    }

}

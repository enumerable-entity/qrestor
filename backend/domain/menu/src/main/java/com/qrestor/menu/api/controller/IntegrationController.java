package com.qrestor.menu.api.controller;

import com.qrestor.menu.service.MenuItemOptionPositionsService;
import com.qrestor.menu.service.MenuItemsService;
import com.qrestor.menu.service.MenuService;
import com.qrestor.models.Pair;
import com.qrestor.models.dto.DictionaryDTO;
import com.qrestor.models.dto.menu.eximport.MenuAgregateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.qrestor.menu.api.RestEndpoints.INTEGRATION;

@RestController
@RequestMapping(INTEGRATION)
@RequiredArgsConstructor
public class IntegrationController {

    private final MenuService menuService;
    private final MenuItemsService menuItemsService;
    private final MenuItemOptionPositionsService menuItemOptionsService;

    @PreAuthorize("hasRole('RESTAURATEUR')")
    @RequestMapping("/restaurant/{restaurantId}/menus")
    public ResponseEntity<MenuAgregateDTO> getRestaurantMenusAggregate(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(menuService.getMenuAggregateForRestaurant(restaurantId));
    }

    @PostMapping("/getMenuItemsPricesMap")
    ResponseEntity<Map<UUID, Pair<String, Long>>> getMenuItemsPriceMap(@RequestBody Set<UUID> menuItemIds) {
        return ResponseEntity.ok(menuItemsService.getMenuItemsPriceMap(menuItemIds));
    }

    @PostMapping("/getMenuItemOptionsPositionsPricesMap")
    ResponseEntity<Map<UUID, Pair<String, Long>>> getMenuItemsOptionsPositionsPriceMap(@RequestBody Set<UUID> menuItemOptionsPositionsIds) {
        return ResponseEntity.ok(menuItemOptionsService.getMenuItemsOptionsPositionsPriceMap(menuItemOptionsPositionsIds));
    }

    @GetMapping("/getMenuCombo")
    Collection<DictionaryDTO<String>> getMenuCombo() {
        return menuService.getMenuCombo();
    }

}

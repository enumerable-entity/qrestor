package com.qrestor.menu.api.controller;

import com.qrestor.commons.menu.dto.MenuItemOptionDTO;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.api.dto.list.MenuListDTO;
import com.qrestor.menu.service.MenuPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RestEndpoints.MENU)
@RequiredArgsConstructor
public class MenuController {

    private final MenuPublicService menuPublicService;

    @RequestMapping("/{restId}")
    public ResponseEntity<List<MenuListDTO>> getActiveMenuForRestaurant(@PathVariable UUID restId) {
        return new ResponseEntity<>(menuPublicService.getActiveMenu(restId), HttpStatus.OK);
    }

    @RequestMapping("/{menuId}/{menuItemId}")
    public ResponseEntity<List<MenuItemOptionDTO>> getMenuItemOptions(@PathVariable UUID menuId, @PathVariable UUID menuItemId) {
        return new ResponseEntity<>(menuPublicService.getMenuItemOptions(menuId, menuItemId), HttpStatus.OK);
    }

}

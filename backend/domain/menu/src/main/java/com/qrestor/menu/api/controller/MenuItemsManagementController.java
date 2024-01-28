package com.qrestor.menu.api.controller;

import com.qrestor.commons.CrudController;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.api.dto.MenuItemDTO;
import com.qrestor.menu.service.MenuItemsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestEndpoints.MENU_ITEMS_MANAGEMENT)
@PreAuthorize("hasRole('RESTAURATEUR')")
public class MenuItemsManagementController extends CrudController<MenuItemDTO> {
    public MenuItemsManagementController(MenuItemsService crudService) {
        super(crudService);
    }
}

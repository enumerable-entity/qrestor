package com.qrestor.menu.api.controller;

import com.qrestor.commons.CrudController;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.api.dto.MenuDTO;
import com.qrestor.menu.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestEndpoints.MENU_MANAGEMENT)
@PreAuthorize("hasRole('RESTAURATEUR')")
public class MenuManagementController extends CrudController<MenuDTO> {
    public MenuManagementController(MenuService crudService) {
        super(crudService);
    }
}

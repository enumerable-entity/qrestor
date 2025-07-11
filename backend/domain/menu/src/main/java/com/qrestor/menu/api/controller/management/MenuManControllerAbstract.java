package com.qrestor.menu.api.controller.management;

import com.qrestor.commons.AbstractCrudController;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.api.dto.MenuDTO;
import com.qrestor.menu.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestEndpoints.MENU_MANAGEMENT)
@PreAuthorize("hasRole('RESTAURATEUR')")
public class MenuManControllerAbstract extends AbstractCrudController<MenuDTO> {
    public MenuManControllerAbstract(MenuService crudService) {
        super(crudService);
    }
}

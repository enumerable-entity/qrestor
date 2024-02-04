package com.qrestor.menu.api.controller.management;

import com.qrestor.commons.CrudController;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.service.impl.MenuItemOptionPositionsServiceImpl;
import com.qrestor.models.dto.menu.MenuItemOptionPositionDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestEndpoints.MENU_ITEM_OPTION_POSITIONS_MANAGEMENT)
@PreAuthorize("hasRole('RESTAURATEUR')")
public class OptionPositionsManController extends CrudController<MenuItemOptionPositionDTO> {
    public OptionPositionsManController(MenuItemOptionPositionsServiceImpl crudService) {
        super(crudService);
    }
}

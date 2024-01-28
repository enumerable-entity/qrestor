package com.qrestor.menu.api.controller.menu;

import com.qrestor.commons.CrudController;
import com.qrestor.commons.CrudService;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.api.dto.MenuDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestEndpoints.MANAGEMENT)
@PreAuthorize("hasRole('ROLE_RESTAURATEUR')")
public class MenuManagementController {//extends CrudController<MenuDTO> {
//    public MenuManagementController(CrudService<MenuDTO> crudService) {
//        super(crudService);
//    }
}

package com.qrestor.menu.api.controller;

import com.qrestor.commons.CrudController;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.api.dto.IngredientDTO;
import com.qrestor.menu.service.IngredientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('RESTAURATEUR')")
@RequestMapping(RestEndpoints.INGREDIENTS_MANAGEMENT)
public class IngredientManagementController extends CrudController<IngredientDTO> {
    public IngredientManagementController(IngredientService crudService) {
        super(crudService);
    }
}

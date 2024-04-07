package com.qrestor.sellpoint.api.controller;

import com.qrestor.commons.AbstractCrudController;
import com.qrestor.commons.CrudService;
import com.qrestor.sellpoint.api.RestEndpoints;
import com.qrestor.sellpoint.api.dto.RestaurantDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestEndpoints.MANAGEMENT)
@PreAuthorize("hasRole('RESTAURATEUR')")
public class RestaurantManagementControllerAbstract extends AbstractCrudController<RestaurantDTO> {
    public RestaurantManagementControllerAbstract(CrudService<RestaurantDTO> crudService) {
        super(crudService);
    }
}

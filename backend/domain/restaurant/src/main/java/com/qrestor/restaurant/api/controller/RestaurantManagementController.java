package com.qrestor.restaurant.api.controller;

import com.qrestor.commons.CrudController;
import com.qrestor.commons.CrudService;
import com.qrestor.restaurant.api.RestEndpoints;
import com.qrestor.restaurant.api.dto.RestaurantDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestEndpoints.MANAGEMENT)
@PreAuthorize("hasRole('RESTAURATEUR')")
public class RestaurantManagementController extends CrudController<RestaurantDTO> {
    public RestaurantManagementController(CrudService<RestaurantDTO> crudService) {
        super(crudService);
    }
}

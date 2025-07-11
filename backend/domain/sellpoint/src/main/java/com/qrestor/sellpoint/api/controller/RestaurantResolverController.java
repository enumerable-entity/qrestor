package com.qrestor.sellpoint.api.controller;

import com.qrestor.sellpoint.api.RestEndpoints;
import com.qrestor.sellpoint.api.dto.RestaurantDTO;
import com.qrestor.sellpoint.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(RestEndpoints.RESTAURANT)
@RequiredArgsConstructor
public class RestaurantResolverController {

    private final RestaurantService restaurantService;

    @RequestMapping("/{id}")
    public ResponseEntity<RestaurantDTO> resolve(@PathVariable UUID id) {
        return new ResponseEntity<>(restaurantService.findByIdPublic(id), HttpStatus.OK);
    }

}

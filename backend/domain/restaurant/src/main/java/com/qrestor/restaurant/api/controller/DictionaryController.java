package com.qrestor.restaurant.api.controller;

import com.qrestor.models.dto.DictionaryDTO;
import com.qrestor.restaurant.api.RestEndpoints;
import com.qrestor.restaurant.service.ComboServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static com.qrestor.restaurant.api.RestEndpoints.RESTAURANT_COMBO;

@RestController
@RequestMapping(RestEndpoints.DICTIONARY)
@RequiredArgsConstructor
@PreAuthorize("hasRole('RESTAURATEUR')")
public class DictionaryController {

    private final ComboServiceImpl comboServiceImpl;

    @RequestMapping(RESTAURANT_COMBO)
    public ResponseEntity<Collection<DictionaryDTO<String>>> restaurantCombo() {
        return ResponseEntity.ok(comboServiceImpl.getRestaurantCombo());
    }
}

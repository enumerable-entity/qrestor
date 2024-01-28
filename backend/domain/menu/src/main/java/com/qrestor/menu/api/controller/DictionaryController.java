package com.qrestor.menu.api.controller;

import com.qrestor.commons.dto.DictionaryDTO;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.service.ComboServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static com.qrestor.menu.api.RestEndpoints.RESTAURANT_COMBO;

@RestController
@RequestMapping(RestEndpoints.DICTIONARY)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_RESTAURANT')")
public class DictionaryController {

    private final ComboServiceImpl comboServiceImpl;

    @RequestMapping(RESTAURANT_COMBO)
    public ResponseEntity<Collection<DictionaryDTO<String>>> restaurantCombo() {
        return ResponseEntity.ok(comboServiceImpl.getRestaurantCombo());
    }
}

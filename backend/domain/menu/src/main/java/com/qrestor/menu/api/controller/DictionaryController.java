package com.qrestor.menu.api.controller;

import com.qrestor.commons.dto.DictionaryDTO;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.UUID;

import static com.qrestor.menu.api.RestEndpoints.*;

@RestController
@RequestMapping(RestEndpoints.DICTIONARY)
@RequiredArgsConstructor
@PreAuthorize("hasRole('RESTAURATEUR')")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @RequestMapping(CATEGORY_COMBO)
    public ResponseEntity<Collection<DictionaryDTO<String>>> getCategoryCombo() {
        return ResponseEntity.ok(dictionaryService.getCategoryCombo());
    }

    @RequestMapping(INGREDIENTS_COMBO)
    public ResponseEntity<Collection<DictionaryDTO<String>>> getIngredientCombo() {
        return ResponseEntity.ok(dictionaryService.getIngredientCombo());
    }

    @RequestMapping(ITEM_OPTIONS_COMBO)
    public ResponseEntity<Collection<DictionaryDTO<String>>> getMenuItemOptionsCombo(
            @RequestParam(required = false) UUID menuItemId) {
        return ResponseEntity.ok(dictionaryService.getMenuItemOptionsCombo(menuItemId));
    }

    @RequestMapping(ITEM_OPTION_POSITIONS_COMBO)
    public ResponseEntity<Collection<DictionaryDTO<String>>> getMenuItemOptionsPositionsCombo(
            @RequestParam(required = false) UUID menuItemOptionId) {
        return ResponseEntity.ok(dictionaryService.getMenuItemOptionsPositionsCombo(menuItemOptionId));
    }
}

package com.qrestor.menu.api.controller;

import com.qrestor.commons.dto.DictionaryDTO;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static com.qrestor.menu.api.RestEndpoints.CATEGORY_COMBO;
import static com.qrestor.menu.api.RestEndpoints.INGREDIENTS_COMBO;

@RestController
@RequestMapping(RestEndpoints.DICTIONARY)
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_RESTAURANT')")
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
}

package com.qrestor.menu.api.controller;

import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.api.dto.ItemCategoryDTO;
import com.qrestor.menu.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(RestEndpoints.CATEGORY)
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ItemCategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.findTotallyAll());
    }
}

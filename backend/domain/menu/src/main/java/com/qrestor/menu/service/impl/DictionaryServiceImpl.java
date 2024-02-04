package com.qrestor.menu.service.impl;

import com.qrestor.menu.repository.*;
import com.qrestor.menu.service.DictionaryService;
import com.qrestor.models.dto.DictionaryDTO;
import com.qrestor.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final MenuRepository repository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;
    private final MenuItemOptionsRepository menuItemOptionsRepository;
    private final MenuItemOptionPositionsRepository optionPositionsRepository;

    @Override
    public Collection<DictionaryDTO<String>> getCategoryCombo() {
        return categoryRepository.getCategoryCombo();
    }

    @Override
    public Collection<DictionaryDTO<String>> getIngredientCombo() {
        return ingredientRepository.getIngredientsCombo(SecurityUtils.getPrincipalUUID());
    }

    @Override
    public Collection<DictionaryDTO<String>> getMenuItemOptionsCombo(UUID menuItemId) {
        return menuItemOptionsRepository.getMenuItemOptionsCombo(SecurityUtils.getPrincipalUUID(), menuItemId);
    }

    @Override
    public Collection<DictionaryDTO<String>> getMenuItemOptionsPositionsCombo(UUID menuItemOptionId) {
        return optionPositionsRepository.getMenuItemOptionsPositionsCombo(SecurityUtils.getPrincipalUUID(), menuItemOptionId);
    }
}

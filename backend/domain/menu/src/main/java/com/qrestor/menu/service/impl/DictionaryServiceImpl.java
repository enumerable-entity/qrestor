package com.qrestor.menu.service.impl;

import com.qrestor.commons.dto.DictionaryDTO;
import com.qrestor.menu.repository.CategoryRepository;
import com.qrestor.menu.repository.IngredientRepository;
import com.qrestor.menu.repository.MenuRepository;
import com.qrestor.menu.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {

    private final MenuRepository repository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public Collection<DictionaryDTO<String>> getCategoryCombo() {
        return categoryRepository.getCategoryCombo();
    }

    @Override
    public Collection<DictionaryDTO<String>> getIngredientCombo() {
        return ingredientRepository.getIngredientsCombo();
    }
}

package com.qrestor.menu.service;

import com.qrestor.commons.dto.DictionaryDTO;

import java.util.Collection;

public interface DictionaryService {
    Collection<DictionaryDTO<String>> getCategoryCombo();

    Collection<DictionaryDTO<String>> getIngredientCombo();
}

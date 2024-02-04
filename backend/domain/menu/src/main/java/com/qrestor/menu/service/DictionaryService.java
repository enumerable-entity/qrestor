package com.qrestor.menu.service;


import com.qrestor.models.dto.DictionaryDTO;

import java.util.Collection;
import java.util.UUID;

public interface DictionaryService {
    Collection<DictionaryDTO<String>> getCategoryCombo();

    Collection<DictionaryDTO<String>> getIngredientCombo();

    Collection<DictionaryDTO<String>> getMenuItemOptionsCombo(UUID menuItemId);

    Collection<DictionaryDTO<String>> getMenuItemOptionsPositionsCombo(UUID menuItemOptionId);
}

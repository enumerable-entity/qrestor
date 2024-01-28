package com.qrestor.menu.service;

import com.qrestor.commons.dto.DictionaryDTO;
import com.qrestor.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ComboServiceImpl {

    private final MenuRepository repository;

    public Collection<DictionaryDTO<String>> getRestaurantCombo() {
        return null;
    }
}

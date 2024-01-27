package com.qrestor.restaurant.service;

import com.qrestor.commons.dto.DictionaryDTO;
import com.qrestor.commons.security.SecurityUtils;
import com.qrestor.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ComboServiceImpl {

    private final RestaurantRepository repository;

    public Collection<DictionaryDTO<String>> getRestaurantCombo() {
        return repository.getRestaurantCombo(SecurityUtils.getPrincipalUUID());
    }
}

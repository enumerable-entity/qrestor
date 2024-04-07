package com.qrestor.restaurant.service;

import com.qrestor.models.dto.DictionaryDTO;
import com.qrestor.restaurant.repository.RestaurantRepository;
import com.qrestor.security.SecurityUtils;
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

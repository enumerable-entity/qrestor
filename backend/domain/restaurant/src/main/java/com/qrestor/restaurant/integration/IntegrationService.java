package com.qrestor.restaurant.integration;

import com.qrestor.commons.dto.PermissionCheckResponse;
import com.qrestor.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final RestaurantService restaurantService;

    public ResponseEntity<PermissionCheckResponse> checkRestaurantOwnership(UUID restaurantId) {
        return ResponseEntity.ok(restaurantService.checkOwnership(restaurantId));
    }
}

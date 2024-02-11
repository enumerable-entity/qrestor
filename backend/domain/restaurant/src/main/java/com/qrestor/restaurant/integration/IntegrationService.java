package com.qrestor.restaurant.integration;

import com.qrestor.models.dto.PermissionCheckResponse;
import com.qrestor.restaurant.service.RestaurantService;
import com.qrestor.restaurant.systemuser.service.SyncUserService;
import com.qrestor.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final RestaurantService restaurantService;
    private final SyncUserService syncUserService;

    public ResponseEntity<PermissionCheckResponse> checkRestaurantOwnership(UUID restaurantId) {
        return ResponseEntity.ok(restaurantService.checkOwnership(restaurantId));
    }

    public ResponseEntity<String> getWaiterRestaurantId() {
        UUID waiterId = SecurityUtils.getPrincipalUUID();
        return ResponseEntity.ok(syncUserService.getRestaurantIdForWaiter(waiterId).toString());
    }
}

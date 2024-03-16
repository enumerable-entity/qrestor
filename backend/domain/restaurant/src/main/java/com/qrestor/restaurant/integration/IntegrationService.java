package com.qrestor.restaurant.integration;

import com.qrestor.models.dto.DictionaryDTO;
import com.qrestor.models.dto.PermissionCheckResponse;
import com.qrestor.models.dto.RestaurantBasicInfoDTO;
import com.qrestor.restaurant.service.ComboServiceImpl;
import com.qrestor.restaurant.service.RestaurantService;
import com.qrestor.restaurant.systemuser.service.SyncUserService;
import com.qrestor.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final RestaurantService restaurantService;
    private final SyncUserService syncUserService;
    private final ComboServiceImpl comboServiceImpl;

    public ResponseEntity<PermissionCheckResponse> checkRestaurantOwnership(UUID restaurantId) {
        return ResponseEntity.ok(restaurantService.checkOwnership(restaurantId));
    }

    public List<RestaurantBasicInfoDTO> getUserRestaurants() {
        List<String> roles = SecurityUtils.getPrincipalRoles();
        if(roles.contains("WAITER")) {
            return syncUserService.getRestaurantIdForWaiter(SecurityUtils.getPrincipalUUID());
        } else{
            return restaurantService.getRestaurantsInfoForUser();
        }
    }

    public UUID getRestaurantOwnerId(UUID restaurantId) {
        return restaurantService.getRestaurantOwnerId(restaurantId);
    }

    public Collection<DictionaryDTO<String>> getRestaurantsDict() {
            return comboServiceImpl.getRestaurantCombo();
    }
}

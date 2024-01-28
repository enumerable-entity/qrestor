package com.qrestor.menu.validator;

import com.qrestor.commons.dto.PermissionCheckResponse;
import com.qrestor.menu.api.dto.MenuDTO;
import com.qrestor.menu.client.RestaurantHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuValidator {

    private final RestaurantHttpClient restaurantClient;

    public void validateAdd(MenuDTO dto) {
        PermissionCheckResponse response = restaurantClient.checkRestaurantOwnership(dto.getRestaurantId());
        if (!response.hasPermission()) {
            throw new RuntimeException("You are not allowed to add menu to this restaurant");
        }
    }
}

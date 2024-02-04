package com.qrestor.menu.client;

import com.qrestor.models.dto.PermissionCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "restaurant", configuration = FeignClientConfig.class)
public interface RestaurantHttpClient {

    @GetMapping("/integration/userOwnershipCheck")
    PermissionCheckResponse checkRestaurantOwnership(@RequestParam UUID restaurantId);
}

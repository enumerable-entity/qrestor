package com.qrestor.restaurant.integration;

import com.qrestor.models.dto.PermissionCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.qrestor.restaurant.api.RestEndpoints.INTEGRATION;

@RestController
@RequestMapping(INTEGRATION)
@RequiredArgsConstructor
public class IntegrationController {
    private final IntegrationService integrationService;

    @GetMapping("/userOwnershipCheck")
    @PreAuthorize("hasRole('RESTAURATEUR')")
    public ResponseEntity<PermissionCheckResponse> checkRestaurantOwnership(@RequestParam UUID restaurantId) {
        return integrationService.checkRestaurantOwnership(restaurantId);
    }

    @GetMapping("/getWaiterRestaurantId")
    @PreAuthorize("hasRole('WAITER')")
    ResponseEntity<String> getWaiterRestaurantId(){
        return integrationService.getWaiterRestaurantId();
    }
}

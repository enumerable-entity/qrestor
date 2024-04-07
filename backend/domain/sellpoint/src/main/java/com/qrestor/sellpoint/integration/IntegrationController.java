package com.qrestor.sellpoint.integration;

import com.qrestor.models.dto.DictionaryDTO;
import com.qrestor.models.dto.PermissionCheckResponse;
import com.qrestor.models.dto.RestaurantBasicInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.qrestor.sellpoint.api.RestEndpoints.INTEGRATION;

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
    @PreAuthorize("hasAnyRole('WAITER', 'RESTAURATEUR')")
    public List<RestaurantBasicInfoDTO> getUserRestaurants() {
        return integrationService.getUserRestaurants();
    }

    @PostMapping("/getRestaurantOwnerId")
    public UUID getRestaurantOwnerId(@RequestBody UUID restaurantId) {
        return integrationService.getRestaurantOwnerId(restaurantId);
    }

    @GetMapping("/getRestaurantsDict")
    @PreAuthorize("hasAnyRole('WAITER', 'RESTAURATEUR')")
    public Collection<DictionaryDTO<String>> getRestaurantsDict() {
        return integrationService.getRestaurantsDict();
    }
}

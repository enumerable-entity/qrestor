package com.qrestor.orders.api.controller;

import com.qrestor.models.dto.order.OrderStatus;
import com.qrestor.models.dto.order.OrderDTO;
import com.qrestor.orders.service.IntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.qrestor.orders.api.RestEndpoints.INTEGRATION;

@RestController
@RequestMapping(INTEGRATION)
@RequiredArgsConstructor
public class IntegrationController {

    private final IntegrationService integrationService;

    @PreAuthorize("hasRole('WAITER')")
    @GetMapping("/{restaurantId}/getOrders")
    ResponseEntity<List<OrderDTO>> getOrdersForRestaurant(@PathVariable UUID restaurantId,
                                                          @RequestParam Set<OrderStatus> orderStatus) {
        return ResponseEntity.ok(integrationService.getOrdersForRestaurant(restaurantId, orderStatus));
    }
}

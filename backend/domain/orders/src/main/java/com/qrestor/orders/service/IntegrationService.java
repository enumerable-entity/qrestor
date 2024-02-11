package com.qrestor.orders.service;

import com.qrestor.models.dto.order.OrderStatus;
import com.qrestor.models.dto.order.OrderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final OrderService orderService;

    public List<OrderDTO> getOrdersForRestaurant(UUID restaurantId, Set<OrderStatus> orderStatus) {
        return orderService.getOrdersForRestaurant(restaurantId, orderStatus);
    }
}

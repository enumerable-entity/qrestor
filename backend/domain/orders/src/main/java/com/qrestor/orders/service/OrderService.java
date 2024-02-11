package com.qrestor.orders.service;

import com.qrestor.commons.enums.OrderStatus;
import com.qrestor.models.dto.order.OrderDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OrderService {
    OrderDTO placeOrder(OrderDTO orderDTO);

    List<OrderDTO> getOrdersForRestaurant(UUID restaurantId, Set<OrderStatus> orderStatus);
}

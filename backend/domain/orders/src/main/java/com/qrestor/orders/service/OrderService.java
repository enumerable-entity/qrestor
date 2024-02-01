package com.qrestor.orders.service;

import com.qrestor.orders.api.dto.OrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    OrderDTO placeOrder(OrderDTO orderDTO);
}

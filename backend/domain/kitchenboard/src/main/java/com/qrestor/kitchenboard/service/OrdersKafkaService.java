package com.qrestor.kitchenboard.service;

import com.qrestor.models.dto.order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersKafkaService {
    private final SseService sseService;

    public void handle(OrderDTO message) {
        log.info("MESSAGE CONSUMED FOR RESTAURANT ID: {}", message.getRestaurantId());
        sseService.emitOrderToWaiterDashboard(message);
    }
}

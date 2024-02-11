package com.qrestor.kitchenboard.kafka;

import com.qrestor.kitchenboard.service.OrdersKafkaService;
import com.qrestor.models.dto.order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.qrestor.commons.kafka.KafkaTopics.ORDERS_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaOrdersListener {

    private final OrdersKafkaService ordersKafkaService;

    @KafkaListener(topics = ORDERS_TOPIC)
    public void consume(OrderDTO message) {
        ordersKafkaService.handle(message);
        log.info("MESSAGE CONSUMED: {}", message);
    }
}
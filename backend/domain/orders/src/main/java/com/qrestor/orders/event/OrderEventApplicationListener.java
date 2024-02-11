package com.qrestor.orders.event;

import com.qrestor.models.dto.order.OrderDTO;
import com.qrestor.orders.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventApplicationListener implements ApplicationListener<OrderEvent> {
    private final KafkaProducer<OrderDTO> orderProducer;

    @Override
    public void onApplicationEvent(OrderEvent event) {
        orderProducer.send(event.getOrderData());
        log.info("Order send to kafka: {}", event.getOrderData().getPublicId());
    }
}

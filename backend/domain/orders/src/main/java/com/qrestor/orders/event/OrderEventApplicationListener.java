package com.qrestor.orders.event;

import com.qrestor.models.dto.kafka.OrderEventDTO;
import com.qrestor.orders.kafka.IKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventApplicationListener implements ApplicationListener<OrderEvent> {
    private final IKafkaProducer<OrderEventDTO> orderProducer;

    @Override
    public void onApplicationEvent(OrderEvent event) {
        orderProducer.send(new OrderEventDTO(event.getOrderEventType(), event.getOrderData()));
        log.info("Order send to kafka: {}", event.getOrderData().getPublicId());
    }
}

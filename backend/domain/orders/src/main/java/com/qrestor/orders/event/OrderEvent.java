package com.qrestor.orders.event;

import com.qrestor.models.dto.kafka.OrderEventType;
import com.qrestor.models.dto.order.OrderDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderEvent extends ApplicationEvent {
    private final OrderEventType orderEventType;
    private final OrderDTO orderData;

    public OrderEvent(Object source, OrderEventType orderEventType, OrderDTO orderData) {
        super(source);
        this.orderEventType = orderEventType;
        this.orderData = orderData;
    }
}

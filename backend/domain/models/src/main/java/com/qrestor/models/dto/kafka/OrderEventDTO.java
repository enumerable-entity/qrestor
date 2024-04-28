package com.qrestor.models.dto.kafka;

import com.qrestor.models.dto.order.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEventDTO {
    private OrderEventType eventType;
    private OrderDTO order;
}

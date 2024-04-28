package com.qrestor.paymentor.kafka;

import com.qrestor.models.dto.kafka.OrderEventDTO;
import com.qrestor.paymentor.service.OrderConsumerService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.qrestor.commons.kafka.KafkaTopics.ORDERS_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaOrdersListener {

    private final OrderConsumerService orderConsumerService;

    @KafkaListener(topics = ORDERS_TOPIC)
    public void consume(OrderEventDTO message) throws StripeException {
        orderConsumerService.process(message);
        log.info("NEW ORDER CONSUMED: {}", message.getOrder());
    }
}
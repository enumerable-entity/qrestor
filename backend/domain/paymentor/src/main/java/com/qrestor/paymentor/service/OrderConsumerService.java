package com.qrestor.paymentor.service;

import com.qrestor.models.dto.order.OrderDTO;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderConsumerService {
    private final PaymentService paymentService;

    public void process(OrderDTO message) throws StripeException {
        if (message.isPaymentSelected()) {
            log.info("Order with payment selected: {}", message);
            paymentService.processPayment(message);
        }
    }
}

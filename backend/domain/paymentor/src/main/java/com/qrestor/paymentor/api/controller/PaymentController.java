package com.qrestor.paymentor.api.controller;

import com.qrestor.models.dto.order.OrderDTO;
import com.qrestor.paymentor.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/getPaymentRedirectUrl")
    public ResponseEntity<String> makeOrderPayment(@RequestBody OrderDTO order) throws StripeException {
        String redirectUrl = paymentService.getPaymentRedirectUrl(order);
        return ResponseEntity.ok().body(redirectUrl);
    }
}

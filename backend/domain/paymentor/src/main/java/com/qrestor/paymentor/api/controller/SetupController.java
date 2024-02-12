package com.qrestor.paymentor.api.controller;

import com.qrestor.paymentor.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SetupController {
    private final PaymentService paymentService;

    @PostMapping("/createStripeAccount")
    @PreAuthorize("hasRole('RESTAURATEUR')")
    public ResponseEntity<Void> createStripeAccount() throws StripeException {
        paymentService.performStripeAccountCreation();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stripe-onboard")
    @PreAuthorize("hasRole('RESTAURATEUR')")
    public ResponseEntity<String> stripeOnboard() throws StripeException {
        String url = paymentService.getStripeOnboardRedirectUrl();
        log.info("Stripe onboard URL: {}", url);
        return ResponseEntity.ok().body(url);
    }


}

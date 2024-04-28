package com.qrestor.paymentor.api.controller;

import com.qrestor.paymentor.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    private static StripeObject getStripeObject(Event paymentEvent) {
        EventDataObjectDeserializer dataObjectDeserializer = paymentEvent.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            log.error("Failed to deserialize stripe object");
        }
        return stripeObject;
    }

    @GetMapping("/order/{orderId}/getPaymentRedirectUrl")
    public ResponseEntity<String> getPaymentURL(@PathVariable UUID orderId) throws StripeException {
        String redirectUrl = paymentService.getRedirectUrlForOrder(orderId);
        return ResponseEntity.ok().body(redirectUrl);
    }

    @GetMapping("/success")
    public ResponseEntity<Void> paymentSuccess() {
        log.info("Payment success:");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/failure")
    public ResponseEntity<Void> paymentFailure() {
        log.info("Payment failure");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> webhook(@RequestBody String invoiceDetails) {
        Event paymentEvent = ApiResource.GSON.fromJson(invoiceDetails, Event.class);
        StripeObject stripeObject = getStripeObject(paymentEvent);

        if (paymentEvent.getType().equals("checkout.session.completed")) {
            Session session = (Session) stripeObject;
            paymentService.processPaymentSuccessAfterActions(session);
        } else {
            log.info("Unhandled event type: {}", paymentEvent.getType());
        }
        return ResponseEntity.ok().build();
    }
}

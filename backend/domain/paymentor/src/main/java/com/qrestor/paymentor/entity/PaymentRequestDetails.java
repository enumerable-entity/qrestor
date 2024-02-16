package com.qrestor.paymentor.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@RedisHash("PaymentDetails")
@AllArgsConstructor
public class PaymentRequestDetails implements Serializable {
    @Id
    private UUID id;
    private String paymentUrl;
}

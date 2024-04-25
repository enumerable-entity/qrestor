package com.qrestor.auth.kafka.producers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class MockKafkaProd<T> implements IKafkaProducer<T>{
    @Override
    public void send(T emailSendRequestDTO) {
        System.out.println("MockKafkaProd.send");
    }
}

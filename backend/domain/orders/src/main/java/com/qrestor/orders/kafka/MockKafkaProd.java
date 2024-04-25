package com.qrestor.orders.kafka;

import org.springframework.beans.factory.annotation.Qualifier;
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

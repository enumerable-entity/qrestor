package com.qrestor.orders.kafka;

public interface IKafkaProducer<T> {
    void send(T emailSendRequestDTO);
}

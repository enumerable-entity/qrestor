package com.qrestor.auth.kafka.producers;

public interface IKafkaProducer<T> {
    void send(T emailSendRequestDTO);
}

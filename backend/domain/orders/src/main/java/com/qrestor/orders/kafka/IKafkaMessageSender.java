package com.qrestor.orders.kafka;

public interface IKafkaMessageSender<T> {
    void send(String topic,
              T message);
}

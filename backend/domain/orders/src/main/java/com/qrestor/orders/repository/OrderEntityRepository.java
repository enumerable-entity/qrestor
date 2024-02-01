package com.qrestor.orders.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.orders.entity.OrderEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEntityRepository extends PublicRepository<OrderEntity, Long> {
}
package com.qrestor.orders.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.commons.enums.OrderStatus;
import com.qrestor.orders.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OrderEntityRepository extends PublicRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByRestaurantIdAndStatusIn(UUID restaurantId, Set<OrderStatus> orderStatus);
}
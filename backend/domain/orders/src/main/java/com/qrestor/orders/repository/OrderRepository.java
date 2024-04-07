package com.qrestor.orders.repository;

import com.qrestor.commons.PublicRepository;
import com.qrestor.models.dto.order.OrderStatus;
import com.qrestor.orders.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface OrderRepository extends PublicRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByRestaurantIdAndStatusIn(UUID restaurantId,
                                                       Set<OrderStatus> orderStatus);

    Page<OrderEntity> findAllByRestaurantIdInAndStatusInAndOrderDateBetween(
            Set<UUID> restaurantId,
            Set<OrderStatus> statuses,
            LocalDateTime dateFrom,
            LocalDateTime dateTo,
            Pageable pageable);
}
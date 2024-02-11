package com.qrestor.orders.service.impl;

import com.qrestor.commons.Utils;
import com.qrestor.commons.enums.OrderStatus;
import com.qrestor.models.dto.order.OrderDTO;
import com.qrestor.orders.entity.OrderEntity;
import com.qrestor.orders.event.OrderEvent;
import com.qrestor.orders.event.OrderEventType;
import com.qrestor.orders.mapper.OrderMapper;
import com.qrestor.orders.repository.OrderEntityRepository;
import com.qrestor.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderEntityRepository orderEntityRepository;
    private final OrderMapper orderMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public OrderDTO placeOrder(OrderDTO orderDTO) {
        orderDTO.setPublicId(Utils.generatePublicId());
        OrderEntity newOrder = orderMapper.toEntity(orderDTO);
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setOrderDate(LocalDateTime.now());
        OrderEntity newSavedOrder = orderEntityRepository.saveAndFlush(newOrder);
        eventPublisher.publishEvent(
                new OrderEvent(this, OrderEventType.NEW, orderDTO));
        log.info("New order placed with id: {}", newSavedOrder.getPublicId());
        return orderMapper.toDto(newSavedOrder);
    }

    @Override
    public List<OrderDTO> getOrdersForRestaurant(UUID restaurantId, Set<OrderStatus> orderStatus) {
        return orderMapper.toDto(orderEntityRepository.findAllByRestaurantIdAndStatusIn(restaurantId, orderStatus));
    }
}

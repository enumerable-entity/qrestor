package com.qrestor.orders.service.impl;

import com.qrestor.commons.Utils;
import com.qrestor.models.dto.order.ItemOrderDetails;
import com.qrestor.models.dto.order.OrderDTO;
import com.qrestor.models.dto.order.OrderStatus;
import com.qrestor.orders.client.RestaurantHttpClient;
import com.qrestor.orders.entity.OrderEntity;
import com.qrestor.orders.event.OrderEvent;
import com.qrestor.orders.event.OrderEventType;
import com.qrestor.orders.mapper.OrderMapper;
import com.qrestor.orders.repository.OrderRepository;
import com.qrestor.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final RestaurantHttpClient restaurantHttpClient;

    @Override
    @Transactional
    public OrderDTO placeOrder(OrderDTO orderDTO) {
        orderDTO.setPublicId(Utils.generatePublicId());
        OrderEntity newOrder = orderMapper.toEntity(orderDTO);
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setOrderDate(LocalDateTime.now());
        OrderEntity newSavedOrder = orderRepository.saveAndFlush(newOrder);
        eventPublisher.publishEvent(
                new OrderEvent(this, OrderEventType.NEW, orderDTO));
        log.info("New order placed with id: {}", newSavedOrder.getPublicId());
        return orderMapper.toDto(newSavedOrder);
    }

    @Override
    public List<OrderDTO> getOrdersForRestaurant(UUID restaurantId, Set<OrderStatus> orderStatus) {
        return orderMapper.toDto(orderRepository.findAllByRestaurantIdAndStatusIn(restaurantId, orderStatus));
    }

    @Override
    public List<ItemOrderDetails> getOrderItems(UUID orderId) {
        return orderRepository.findByUuid(orderId)
                .map(OrderEntity::getItems)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public void changeOrderStatus(UUID orderId, OrderStatus status) {
        OrderEntity order = orderRepository.findByUuid(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Optional<UUID> waiterRestaurantId = Optional.ofNullable(restaurantHttpClient.getWaiterRestaurantId());
        if (waiterRestaurantId.isPresent() && !waiterRestaurantId.get().equals(order.getRestaurantId())) {
            throw new RuntimeException("Order not found");
        }
        order.setStatus(status);
        orderRepository.saveAndFlush(order);
        eventPublisher.publishEvent(
                new OrderEvent(this, OrderEventType.UPDATE, orderMapper.toDto(order)));
    }

    @Override
    public Page<OrderDTO> getOrdersHistory(LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        Optional<UUID> waiterRestaurantId = Optional.ofNullable(restaurantHttpClient.getWaiterRestaurantId());
        if (waiterRestaurantId.isPresent()) {
            return orderRepository.findAllByRestaurantIdAndOrderDateBetween(waiterRestaurantId.get(),
                    dateFrom.atStartOfDay(), dateTo.atStartOfDay(), pageable).map(orderMapper::toDto);
        } else {
            throw new RuntimeException("Waiter restaurant id not found");
        }
    }
}

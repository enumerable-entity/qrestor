package com.qrestor.orders.service.impl;

import com.qrestor.commons.Utils;
import com.qrestor.models.dto.RestaurantBasicInfoDTO;
import com.qrestor.models.dto.order.ItemOrderDetails;
import com.qrestor.models.dto.order.OrderDTO;
import com.qrestor.models.dto.order.OrderStatus;
import com.qrestor.orders.client.RestaurantHttpClient;
import com.qrestor.orders.entity.OrderEntity;
import com.qrestor.orders.event.OrderEvent;
import com.qrestor.models.dto.kafka.OrderEventType;
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
import java.util.*;
import java.util.stream.Collectors;

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
        if (orderDTO.isPaymentSelected()) {
            newOrder.setStatus(OrderStatus.PAYMENT_IN_PROGRESS);
        } else {
            newOrder.setStatus(OrderStatus.PENDING);
        }

        newOrder.setOrderDate(LocalDateTime.now());
        OrderEntity newSavedOrder = orderRepository.saveAndFlush(newOrder);
        var newOrderDto = orderMapper.toDto(newSavedOrder, new RestaurantBasicInfoDTO(orderDTO.getRestaurantTitle(), orderDTO.getRestaurantName()));
        eventPublisher.publishEvent(
                new OrderEvent(this, OrderEventType.NEW, newOrderDto));
        log.info("New order placed with id: {}", newSavedOrder.getPublicId());
        return newOrderDto;
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
        Optional<List<RestaurantBasicInfoDTO>> waiterRestaurants = Optional.ofNullable(restaurantHttpClient.getWaiterRestaurantId());
        Map<UUID, RestaurantBasicInfoDTO> map = waiterRestaurants.get().stream().collect(Collectors.toMap(RestaurantBasicInfoDTO::getPublicId, res -> res));
        if (!waiterRestaurants.get().isEmpty() && !map.containsKey(order.getRestaurantId())) {
            throw new RuntimeException("Order not found");
        }
        order.setStatus(status);
        orderRepository.saveAndFlush(order);
        eventPublisher.publishEvent(
                new OrderEvent(this, OrderEventType.UPDATE, orderMapper.toDto(order, map.get(order.getRestaurantId()))));
    }

    @Override
    public Page<OrderDTO> getOrdersHistory(LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        Optional<List<RestaurantBasicInfoDTO>> userRestaurants = Optional.ofNullable(restaurantHttpClient.getWaiterRestaurantId());
        if (userRestaurants.isPresent() && !userRestaurants.get().isEmpty()) {
            var restInfoList = userRestaurants.get();
            Map<UUID, RestaurantBasicInfoDTO> map = restInfoList.stream().collect(Collectors.toMap(RestaurantBasicInfoDTO::getPublicId, res -> res));
            return orderRepository.findAllByRestaurantIdInAndStatusInAndOrderDateBetween(map.keySet(), Set.of(OrderStatus.COMPLETED, OrderStatus.CANCELLED),
                    dateFrom.atStartOfDay(), dateTo.plusDays(1).atStartOfDay(), pageable)
                    .map(orderEntity -> orderMapper.toDto(orderEntity, map.get(orderEntity.getRestaurantId())));
        } else {
            throw new RuntimeException("Waiter or restaurateur restaurant id not found");
        }
    }

    @Override
    public Page<OrderDTO> active(LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        Optional<List<RestaurantBasicInfoDTO>> userRestaurants = Optional.ofNullable(restaurantHttpClient.getWaiterRestaurantId());
        Set<OrderStatus> completed = Set.of(OrderStatus.PENDING, OrderStatus.PAYMENT_IN_PROGRESS, OrderStatus.IN_PROGRESS);
        if (userRestaurants.isPresent() && !userRestaurants.get().isEmpty()) {
            var restInfoList = userRestaurants.get();
            Map<UUID, RestaurantBasicInfoDTO> map = restInfoList.stream().collect(Collectors.toMap(RestaurantBasicInfoDTO::getPublicId, res -> res));
            return orderRepository.findAllByRestaurantIdInAndStatusInAndOrderDateBetween(map.keySet(), completed,
                            dateFrom.atStartOfDay(), dateTo.plusDays(1).atStartOfDay(), pageable)
                    .map(orderEntity -> orderMapper.toDto(orderEntity, map.get(orderEntity.getRestaurantId())));
        } else {
            throw new RuntimeException("Waiter or restaurateur restaurant id not found");
        }
    }
}

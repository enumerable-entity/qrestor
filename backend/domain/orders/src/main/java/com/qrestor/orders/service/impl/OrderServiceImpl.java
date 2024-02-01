package com.qrestor.orders.service.impl;

import com.qrestor.commons.Utils;
import com.qrestor.orders.api.dto.OrderDTO;
import com.qrestor.orders.entity.OrderEntity;
import com.qrestor.orders.enums.OrderStatus;
import com.qrestor.orders.mapper.OrderMapper;
import com.qrestor.orders.repository.OrderEntityRepository;
import com.qrestor.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderEntityRepository orderEntityRepository;
    private final OrderMapper orderMapper;
    @Override
    @Transactional
    public OrderDTO placeOrder(OrderDTO orderDTO) {
        orderDTO.setPublicId(Utils.generatePublicId());
        OrderEntity newOrder = orderMapper.toEntity(orderDTO);
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setOrderDate(LocalDateTime.now());
        OrderEntity newSavedOrder = orderEntityRepository.saveAndFlush(newOrder);
        return orderMapper.toDto(newSavedOrder);
    }
}

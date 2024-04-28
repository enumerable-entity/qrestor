package com.qrestor.orders.service;

import com.qrestor.models.dto.order.ItemOrderDetails;
import com.qrestor.models.dto.order.OrderDTO;
import com.qrestor.models.dto.order.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface OrderService {
    OrderDTO placeOrder(OrderDTO orderDTO);

    List<OrderDTO> getOrdersForRestaurant(UUID restaurantId,
                                          Set<OrderStatus> orderStatus);

    List<ItemOrderDetails> getOrderItems(UUID orderId);

    void changeOrderStatus(UUID orderId,
                           OrderStatus status);

    Page<OrderDTO> getOrdersHistory(LocalDate dateFrom,
                                    LocalDate dateTo,
                                    Pageable pageable);

    Page<OrderDTO> active(LocalDate dateFrom,
                          LocalDate dateTo,
                          Pageable pageable);
}

package com.qrestor.kitchenboard.client;

import com.qrestor.models.dto.order.OrderStatus;
import com.qrestor.models.dto.order.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@FeignClient(name = "orders", configuration = FeignClientConfig.class)
public interface OrdersHttpClient {

    @GetMapping("/integration/{restaurantId}/getOrders")
    List<OrderDTO> getOrdersForRestaurant(@PathVariable UUID restaurantId,
                                          @RequestParam Set<OrderStatus> orderStatus);
}

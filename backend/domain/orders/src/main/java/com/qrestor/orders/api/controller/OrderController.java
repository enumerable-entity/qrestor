package com.qrestor.orders.api.controller;

import com.qrestor.models.dto.order.ItemOrderDetails;
import com.qrestor.models.dto.order.OrderDTO;
import com.qrestor.models.dto.order.OrderStatus;
import com.qrestor.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.qrestor.orders.api.RestEndpoints.ORDERS;

@RestController
@RequestMapping(ORDERS)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.placeOrder(orderDTO));
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<ItemOrderDetails>> getOrderItems(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderItems(orderId));
    }

    @PreAuthorize("hasAnyRole('WAITER', 'RESTAURATEUR')")
    @GetMapping("/history")
    public ResponseEntity<Page<OrderDTO>> getOrdersHistory(@RequestParam LocalDate dateFrom,
                                                           @RequestParam LocalDate dateTo,
                                                           Pageable pageable) {

        return ResponseEntity.ok(orderService.getOrdersHistory(dateFrom, dateTo, pageable));
    }

    @PreAuthorize("hasAnyRole('WAITER', 'RESTAURATEUR')")
    @PostMapping("/{orderId}/status")
    public ResponseEntity<Void> changeOrderStatus(@PathVariable UUID orderId, @RequestParam OrderStatus status) {
        orderService.changeOrderStatus(orderId, status);
        return ResponseEntity.ok().build();
    }
}

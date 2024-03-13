package com.qrestor.kitchenboard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qrestor.kitchenboard.client.OrdersHttpClient;
import com.qrestor.kitchenboard.client.RestaurantHttpClient;
import com.qrestor.models.dto.order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.qrestor.kitchenboard.enums.SSEvent.*;
import static com.qrestor.models.dto.order.OrderStatus.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SseService {

    private final RestaurantHttpClient restaurantHttpClient;
    private final OrdersHttpClient ordersHttpClient;
    private final ObjectMapper objectMapper;
    private final Map<UUID, SseEmitter> restaurantIdToEmitter = new ConcurrentHashMap<>();

    public void addNewActiveSSEmitter(SseEmitter emitter) {
        String waiterRestaurantId = restaurantHttpClient.getWaiterRestaurantId().stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException("Waiter is not assigned to any restaurant"))
                .getPublicId()
                .toString();
        if (waiterRestaurantId != null) {
            //        emitters.computeIfPresent(principalUUID, (k, v) -> {
//            v.complete();
//            return emitter;
//        });
            log.info("Waiter restaurant id: {}", waiterRestaurantId);
            UUID restaurantId = UUID.fromString(waiterRestaurantId);
            restaurantIdToEmitter.put(restaurantId, emitter);
            emitter.onCompletion(() -> restaurantIdToEmitter.remove(restaurantId));
            emitter.onTimeout(() -> restaurantIdToEmitter.remove(restaurantId));
            initOrdersForWaiterDashboard(restaurantId);
            return;
        }
        throw new RuntimeException("Waiter is not assigned to any restaurant");
    }

    private void initOrdersForWaiterDashboard(UUID restaurantId) {
        ordersHttpClient.getOrdersForRestaurant(restaurantId, Set.of(PENDING, IN_PROGRESS, PAYMENT_IN_PROGRESS)).forEach(orderDTO -> {
            try {
                Set<ResponseBodyEmitter.DataWithMediaType> newOrderSSEvent =
                        SseEmitter
                                .event()
                                .id(orderDTO.getPublicId().toString())
                                .name(INIT_ORDERS.name())
                                .reconnectTime(10000L)
                                .comment("Init order has been received.")
                                .data(objectMapper.writeValueAsString(orderDTO), MediaType.APPLICATION_JSON)
                                .build();
                restaurantIdToEmitter.get(restaurantId).send(newOrderSSEvent);
            } catch (IOException e) {
                restaurantIdToEmitter.get(restaurantId).completeWithError(e);
                restaurantIdToEmitter.remove(restaurantId);
            }
        });
    }

    @Async
    public void emitWaiterRequest(UUID restaurantId, int tableNr) {
        SseEmitter restaurantEmitter = restaurantIdToEmitter.get(restaurantId);
        if (restaurantEmitter != null) {
            try {
                Set<ResponseBodyEmitter.DataWithMediaType> waiterRequestSSEvent =
                        SseEmitter
                                .event()
                                .id(UUID.randomUUID().toString())
                                .name(WAITER_CALL.name())
                                .reconnectTime(10000L)
                                .comment("Waiter request for table " + tableNr)
                                .data(tableNr, MediaType.TEXT_PLAIN)
                                .build();
                restaurantEmitter.send(waiterRequestSSEvent);
            } catch (IOException e) {
                restaurantEmitter.completeWithError(e);
                restaurantIdToEmitter.remove(restaurantId);
            }
        }
    }

    public void emitOrderToWaiterDashboard(OrderDTO message) {
        UUID targetEmitterId = message.getRestaurantId();
        SseEmitter emitter = restaurantIdToEmitter.get(targetEmitterId);
        if (emitter != null) {
            try {
                Set<ResponseBodyEmitter.DataWithMediaType> newOrderSSEvent =
                        SseEmitter
                                .event()
                                .id(message.getPublicId().toString())
                                .name(NEW_ORDER.name())
                                .reconnectTime(10000L)
                                .comment("New order has been placed.")
                                .data(objectMapper.writeValueAsString(message), MediaType.APPLICATION_JSON)
                                .build();
                emitter.send(newOrderSSEvent);
            } catch (IOException e) {
                emitter.completeWithError(e);
                restaurantIdToEmitter.remove(targetEmitterId);
            }
        }
    }
}

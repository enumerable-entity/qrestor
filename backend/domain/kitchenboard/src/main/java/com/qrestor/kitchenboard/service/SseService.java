package com.qrestor.kitchenboard.service;

import com.qrestor.kitchenboard.client.OrdersHttpClient;
import com.qrestor.kitchenboard.client.RestaurantHttpClient;
import com.qrestor.models.dto.order.OrderDTO;
import lombok.RequiredArgsConstructor;
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

import static com.qrestor.commons.enums.OrderStatus.IN_PROGRESS;
import static com.qrestor.commons.enums.OrderStatus.PENDING;
import static com.qrestor.kitchenboard.enums.SSEvent.*;

@Service
@RequiredArgsConstructor
public class SseService {

    private final RestaurantHttpClient restaurantHttpClient;
    private final OrdersHttpClient ordersHttpClient;
    private final Map<UUID, SseEmitter> restaurantIdToEmitter = new ConcurrentHashMap<>();

    public void addNewActiveSSEmitter(SseEmitter emitter) {
        String waiterRestaurantId = restaurantHttpClient.getWaiterRestaurantId().getBody();
        if (waiterRestaurantId != null) {
            //        emitters.computeIfPresent(principalUUID, (k, v) -> {
//            v.complete();
//            return emitter;
//        });
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
        ordersHttpClient.getOrdersForRestaurant(restaurantId, Set.of(PENDING, IN_PROGRESS)).forEach(orderDTO -> {
            try {
                Set<ResponseBodyEmitter.DataWithMediaType> newOrderSSEvent =
                        SseEmitter
                                .event()
                                .id(orderDTO.getPublicId().toString())
                                .name(ORDERS.name())
                                .reconnectTime(10000L)
                                .comment("Init order has been received.")
                                .data(orderDTO.toString(), MediaType.APPLICATION_JSON)
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
                                .data(message.toString(), MediaType.APPLICATION_JSON)
                                .build();
                emitter.send(newOrderSSEvent);
            } catch (IOException e) {
                emitter.completeWithError(e);
                restaurantIdToEmitter.remove(targetEmitterId);
            }
        }
    }
}

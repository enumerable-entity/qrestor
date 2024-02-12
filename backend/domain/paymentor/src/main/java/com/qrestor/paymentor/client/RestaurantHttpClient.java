package com.qrestor.paymentor.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "restaurant", configuration = FeignClientConfig.class)
public interface RestaurantHttpClient {

    @GetMapping("/integration/getWaiterRestaurantId")
    UUID getWaiterRestaurantId();

    @GetMapping("/integration/getRestaurantOwnerId")
    UUID getRestaurantOwnerId(@RequestBody UUID restaurantId);
}

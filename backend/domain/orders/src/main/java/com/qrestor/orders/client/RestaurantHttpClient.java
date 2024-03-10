package com.qrestor.orders.client;

import com.qrestor.models.dto.RestaurantBasicInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "restaurant", configuration = FeignClientConfig.class)
public interface RestaurantHttpClient {

    @GetMapping("/integration/getWaiterRestaurantId")
    List<RestaurantBasicInfoDTO> getWaiterRestaurantId();
}

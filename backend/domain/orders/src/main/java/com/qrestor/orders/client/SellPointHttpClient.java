package com.qrestor.orders.client;

import com.qrestor.models.dto.RestaurantBasicInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "sell-points", configuration = FeignClientConfig.class)
public interface SellPointHttpClient {

    @GetMapping("/integration/getWaiterRestaurantId")
    List<RestaurantBasicInfoDTO> getWaiterRestaurantId();
}

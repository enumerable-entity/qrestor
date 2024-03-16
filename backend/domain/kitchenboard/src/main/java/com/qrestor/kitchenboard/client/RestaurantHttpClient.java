package com.qrestor.kitchenboard.client;

import com.qrestor.models.dto.RestaurantBasicInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "restaurant", configuration = FeignClientConfig.class)
public interface RestaurantHttpClient {

    @GetMapping("/integration/getWaiterRestaurantId")
    List<RestaurantBasicInfoDTO> getWaiterRestaurantId();
}

package com.qrestor.importer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "menu", configuration = FeignClientConfig.class)
public interface RestaurantHttpClient {

}

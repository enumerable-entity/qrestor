package com.qrestor.resolverqr.client;

import com.qrestor.models.dto.DictionaryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@FeignClient(name = "restaurant", configuration = FeignClientConfig.class)
public interface RestaurantHttpClient {

    @GetMapping("/integration/getRestaurantsDict")
    Collection<DictionaryDTO<String>> getRestaurantsDict();
}

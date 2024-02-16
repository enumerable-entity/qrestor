package com.qrestor.paymentor.client;

import com.qrestor.models.Pair;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@FeignClient(name = "menu", configuration = FeignClientConfig.class)
public interface MenuHttpClient {

    @PostMapping("/integration/getMenuItemsPricesMap")
    Map<UUID, Pair<String, Long>> getMenuItemsPriceMap(@RequestBody Set<UUID> menuItemIds);

    @PostMapping("/integration/getMenuItemOptionsPositionsPricesMap")
    Map<UUID, Pair<String, Long>> getMenuItemOptionsPositionsPricesMap(@RequestBody Set<UUID> menuItemOptionsPositionsIds);
}

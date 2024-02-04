package com.qrestor.importer.client;

import com.qrestor.models.dto.menu.eximport.MenuAgregateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "menu", configuration = FeignClientConfig.class)
public interface MenuServiceClient {

    @GetMapping("/integration/restaurant/{restaurantId}/menus")
    MenuAgregateDTO getMenuAggregate(@PathVariable UUID restaurantId);

}

package com.qrestor.resolverqr.client;

import com.qrestor.models.dto.DictionaryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@FeignClient(name = "menu", configuration = FeignClientConfig.class)
public interface MenuServiceClient {

    @GetMapping("/integration/getMenuCombo")
    Collection<DictionaryDTO<String>> getMenuCombo();

}

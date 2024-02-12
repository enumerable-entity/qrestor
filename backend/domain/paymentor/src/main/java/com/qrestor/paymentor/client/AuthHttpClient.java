package com.qrestor.paymentor.client;

import com.qrestor.models.dto.auth.UserDescriptorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "auth", configuration = FeignClientConfig.class)
public interface AuthHttpClient {

    @GetMapping("/api/v1/authentication/me")
    UserDescriptorDTO getUserInfo();
}

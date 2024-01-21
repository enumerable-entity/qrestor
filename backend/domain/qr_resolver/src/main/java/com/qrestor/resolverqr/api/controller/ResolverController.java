package com.qrestor.resolverqr.api.controller;

import com.qrestor.resolverqr.api.dto.ResolvingResponseDTO;
import com.qrestor.resolverqr.service.ResolverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.qrestor.resolverqr.api.RestEndpoints.QR;

@RequestMapping(QR)
@RestController
@RequiredArgsConstructor
public class ResolverController {
    private final ResolverService resolverService;

    @GetMapping("/{qrCode}")
    public ResolvingResponseDTO resolve(@PathVariable String qrCode) {
        return resolverService.resolve(qrCode);
    }
}

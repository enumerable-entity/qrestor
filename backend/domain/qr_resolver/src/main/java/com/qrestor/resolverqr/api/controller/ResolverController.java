package com.qrestor.resolverqr.api.controller;

import com.qrestor.models.dto.qr.ResolvingResponseDTO;
import com.qrestor.resolverqr.service.ResolverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.qrestor.resolverqr.api.RestEndpoints.QR;

@RequestMapping(QR)
@RestController
@RequiredArgsConstructor
public class ResolverController {
    private final ResolverService resolverService;

    @GetMapping("/{publicId}")
    public ResolvingResponseDTO resolve(@PathVariable UUID publicId) {
        return resolverService.resolve(publicId);
    }
}

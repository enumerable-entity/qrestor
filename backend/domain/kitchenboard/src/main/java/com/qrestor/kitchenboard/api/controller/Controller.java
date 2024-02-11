package com.qrestor.kitchenboard.api.controller;

import com.qrestor.kitchenboard.service.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static com.qrestor.kitchenboard.RestEndpoints.SSE_SUBSCRIBE;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final SseService sseService;

    @GetMapping(path = SSE_SUBSCRIBE, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PreAuthorize("hasRole('RESTAURATEUR')")
    public SseEmitter subscribe() {
        log.info("SSE subscribe request");
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        sseService.addEmitter(emitter);
        log.info("SSE subscribe request completed");
        return emitter;
    }
}

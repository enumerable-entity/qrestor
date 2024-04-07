package com.qrestor.kitchenboard.api.controller;

import com.qrestor.kitchenboard.service.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

import static com.qrestor.kitchenboard.RestEndpoints.SSE_SUBSCRIBE;
import static com.qrestor.kitchenboard.RestEndpoints.WAITER_REQUEST;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final SseService sseService;

    @GetMapping(path = SSE_SUBSCRIBE, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @PreAuthorize("hasAnyRole('WAITER', 'RESTAURATEUR')")
    public SseEmitter subscribe() {
        log.info("SSE subscribe request");
        SseEmitter eventEmitter = new SseEmitter(Long.MAX_VALUE);
        sseService.addNewActiveSSEmitter(eventEmitter);
        log.info("SSE subscribe request completed");
        return eventEmitter;
    }

    @GetMapping(path = WAITER_REQUEST)
    public ResponseEntity<Void> waiterRequest(@RequestParam UUID restaurantId,
                                              @RequestParam int tableNr) {
        log.info("Waiter request for restaurant id {} and table nr {}", restaurantId.toString(), tableNr);
        sseService.emitWaiterRequest(restaurantId, tableNr);
        return ResponseEntity.ok().build();
    }


}

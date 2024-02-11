package com.qrestor.kitchenboard.service;

import com.qrestor.security.SecurityUtils;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SseService {

    private final Map<UUID, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void addEmitter(SseEmitter emitter) {
        UUID principalUUID = SecurityUtils.getPrincipalUUID();
//        emitters.computeIfPresent(principalUUID, (k, v) -> {
//            v.complete();
//            return emitter;
//        });
        emitters.put(principalUUID, emitter);
        emitter.onCompletion(() -> emitters.remove(principalUUID));
        emitter.onTimeout(() -> emitters.remove(principalUUID));
    }

    @Scheduled(fixedRate = 1000)
    public void sendEvents() {
        for (Map.Entry<UUID, SseEmitter> emitter : emitters.entrySet()) {
            try {
                Set<ResponseBodyEmitter.DataWithMediaType> eVentName =
                        SseEmitter
                                .event()
                                .id("1")
                                .name("ordersUpdate")
                                .reconnectTime(10000L)
                                .comment("comment")
                                .data(emitter.getKey().toString(), MediaType.TEXT_PLAIN)
                                .build();
                emitter.getValue().send(eVentName);
            } catch (IOException e) {
                emitter.getValue().complete();
                emitter.getValue().completeWithError(e);
                emitters.remove(emitter.getKey());
            }
        }
    }


}

package com.qrestor.restaurant.systemuser.service;

import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import com.qrestor.restaurant.systemuser.mapper.SyncUserMapper;
import com.qrestor.restaurant.systemuser.repository.SyncUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SyncUserService {

    private final SyncUserRepository syncUserRepository;
    private final SyncUserMapper syncUserMapper;

    public void save(UserKafkaSyncDTO syncDTO) {
        syncUserRepository.saveAndFlush(syncUserMapper.toEntity(syncDTO));
    }

    public UUID getRestaurantIdForWaiter(UUID waiterId) {
        return syncUserRepository.getByUuid(waiterId).orElseThrow(() -> new RuntimeException("Waiter not found")).getRestaurant().getPublicId();
    }
}

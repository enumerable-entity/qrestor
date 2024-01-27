package com.qrestor.restaurant.systemuser.service;

import com.qrestor.commons.kafka.dto.UserKafkaSyncDTO;
import com.qrestor.restaurant.systemuser.mapper.SyncUserMapper;
import com.qrestor.restaurant.systemuser.repository.SyncUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SyncUserService {

    private final SyncUserRepository syncUserRepository;
    private final SyncUserMapper syncUserMapper;

    public void save(UserKafkaSyncDTO syncDTO) {
        syncUserRepository.saveAndFlush(syncUserMapper.toEntity(syncDTO));
    }
}

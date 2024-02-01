package com.qrestor.orders.systemuser.service;

import com.qrestor.commons.kafka.dto.UserKafkaSyncDTO;
import com.qrestor.orders.systemuser.mapper.SyncUserMapper;
import com.qrestor.orders.systemuser.repository.SyncUserRepository;
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

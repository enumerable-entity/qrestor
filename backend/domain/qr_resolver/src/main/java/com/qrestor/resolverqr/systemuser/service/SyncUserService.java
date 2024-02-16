package com.qrestor.resolverqr.systemuser.service;

import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import com.qrestor.resolverqr.systemuser.mapper.SyncUserMapper;
import com.qrestor.resolverqr.systemuser.repository.SyncUserRepository;
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

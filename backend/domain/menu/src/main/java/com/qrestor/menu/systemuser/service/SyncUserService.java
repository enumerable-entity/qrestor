package com.qrestor.menu.systemuser.service;

import com.qrestor.menu.systemuser.mapper.SyncUserMapper;
import com.qrestor.menu.systemuser.repository.SyncUserRepository;
import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
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

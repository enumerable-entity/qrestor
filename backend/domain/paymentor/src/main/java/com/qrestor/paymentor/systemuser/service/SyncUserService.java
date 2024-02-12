package com.qrestor.paymentor.systemuser.service;

import com.qrestor.models.dto.kafka.UserKafkaSyncDTO;
import com.qrestor.paymentor.systemuser.mapper.SyncUserMapper;
import com.qrestor.paymentor.systemuser.repository.SyncUserRepository;
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

package com.qrestor.feedback.systemuser.service;

import com.qrestor.feedback.systemuser.mapper.SyncUserMapper;
import com.qrestor.feedback.systemuser.repository.SyncUserRepository;
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

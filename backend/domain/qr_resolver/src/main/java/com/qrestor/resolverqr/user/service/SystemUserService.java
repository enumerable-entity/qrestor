package com.qrestor.resolverqr.user.service;

import com.qrestor.commons.kafka.dto.UserKafkaSyncDTO;
import com.qrestor.resolverqr.user.mapper.SystemUserMapper;
import com.qrestor.resolverqr.user.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SystemUserService {

    private final SystemUserRepository systemUserRepository;
    private final SystemUserMapper systemUserMapper;

    public void save(UserKafkaSyncDTO syncDTO) {
        systemUserRepository.saveAndFlush(systemUserMapper.toEntity(syncDTO));
    }
}

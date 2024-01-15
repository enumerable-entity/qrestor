package com.qrestor.auth.user.service;

import com.qrestor.auth.user.entity.SystemUserEntity;
import com.qrestor.auth.user.repository.SystemUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SystemUserService {

    private final SystemUserRepository userRepository;

    public Optional<SystemUserEntity> findByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    public Optional<SystemUserEntity> findByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }
}

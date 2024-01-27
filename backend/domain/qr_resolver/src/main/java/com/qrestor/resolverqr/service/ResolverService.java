package com.qrestor.resolverqr.service;

import com.qrestor.resolverqr.api.dto.ResolvingResponseDTO;
import com.qrestor.resolverqr.mapper.QrMapper;
import com.qrestor.resolverqr.repository.QrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResolverService {

    private final QrRepository qrRepository;
    private final QrMapper qrMapper;

    public ResolvingResponseDTO resolve(UUID publicId) {
        return qrRepository.findByPublicId(publicId)
                .map(qrMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Qr code not found"));
    }
}

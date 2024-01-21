package com.qrestor.resolverqr.service;

import com.qrestor.resolverqr.api.dto.ResolvingResponseDTO;
import com.qrestor.resolverqr.mapper.QrMapper;
import com.qrestor.resolverqr.repository.QrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResolverService {

    private final QrRepository qrRepository;
    private final QrMapper qrMapper;

    public ResolvingResponseDTO resolve(String qrCode) {
        return qrRepository.findByQrCode(qrCode)
                .map(qrMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Qr code not found"));
    }
}

package com.qrestor.exporter.service;

import com.qrestor.exporter.ExportType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExportStrategyFactory {

    private final List<ExportStrategy> exportStrategies;

    public ExportStrategy getExportStrategy(ExportType type) {
        return exportStrategies.stream()
                .filter(strategy -> strategy.support(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid export type: " + type));
    }
}

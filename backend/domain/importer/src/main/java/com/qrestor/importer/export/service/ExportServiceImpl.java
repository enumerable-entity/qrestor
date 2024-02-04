package com.qrestor.importer.export.service;

import com.qrestor.importer.export.ExportType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExportServiceImpl implements ExportService {

    private final ExportStrategyFactory exportStrategyFactory;

    @Override
    public void exportData(UUID restaurantId, ExportType format, HttpServletResponse response) throws IOException {
        ExportStrategy exportStrategy = exportStrategyFactory.getExportStrategy(format);
        exportStrategy.exportData(restaurantId, format, response);
    }
}

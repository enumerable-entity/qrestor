package com.qrestor.exporter.service;

import com.qrestor.exporter.ExportType;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

public interface ExportService {
    void exportData(UUID restaurantId,
                    ExportType format,
                    HttpServletResponse response) throws IOException;
}

package com.qrestor.exporter.export.service;

import com.qrestor.exporter.export.ExportType;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

public interface ExportStrategy {
    boolean support(ExportType type);
    void exportData(UUID restaurantId, ExportType format, HttpServletResponse response) throws IOException;
}

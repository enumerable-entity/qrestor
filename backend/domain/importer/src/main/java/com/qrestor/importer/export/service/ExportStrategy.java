package com.qrestor.importer.export.service;

import com.qrestor.importer.export.ExportType;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

public interface ExportStrategy {
    boolean support(ExportType type);
    void exportData(UUID restaurantId, ExportType format, HttpServletResponse response) throws IOException;
}

package com.qrestor.exporter.export.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.qrestor.exporter.client.MenuServiceClient;
import com.qrestor.exporter.export.ExportType;
import com.qrestor.models.dto.menu.eximport.MenuAgregateDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JsonExporter implements ExportStrategy {

    private final ObjectMapper objectMapper;
    private final MenuServiceClient menuServiceClient;

    @Override
    public boolean support(ExportType type) {
        return ExportType.JSON.equals(type);
    }

    @Override
    public void exportData(UUID restaurantId, ExportType format, HttpServletResponse response) throws IOException {
        setupResponseHeaders(response);
        MenuAgregateDTO allMenusForRestaurant = menuServiceClient.getMenuAggregate(restaurantId);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(response.getOutputStream(), allMenusForRestaurant);
    }

    private void setupResponseHeaders(HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"restaurant-menus-export.json\"");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
        response.setHeader(HttpHeaders.EXPIRES, "0");
        response.setContentType("application/json");
    }
}

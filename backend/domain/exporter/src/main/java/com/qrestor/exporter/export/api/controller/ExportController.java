package com.qrestor.exporter.export.api.controller;

import com.qrestor.exporter.export.ExportType;
import com.qrestor.exporter.export.service.ExportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/export")
@PreAuthorize("hasRole('RESTAURATEUR')")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exporterService;

    @GetMapping("/restaurant/{restaurantId}")
    public void exportAllMenusForRestaurant(@PathVariable UUID restaurantId, @RequestParam ExportType format,
                                            HttpServletResponse response) throws IOException {
        exporterService.exportData(restaurantId, format, response);
    }

}

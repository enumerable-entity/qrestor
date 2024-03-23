package com.qrestor.exporter.export.service;

import com.qrestor.exporter.client.MenuServiceClient;
import com.qrestor.exporter.export.ExportType;
import com.qrestor.models.dto.menu.eximport.MenuAgregateDTO;
import com.qrestor.models.dto.menu.eximport.MenuDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class XlsExporter implements ExportStrategy {
    private static final int MENU_HEADER_COLUMN = 0;
    private static final int MENU_ITEMS_HEADERS_COLUMN = 2;
    private static final int MENU_ITEM_INGRIDIENTS_HEADERS_COLUMN = 3;
    private static final int MENU_ITEM_OPTIONS_HEADERS_COLUMN = 3;
    private static final int MENU_ITEM_OPTIONS_POSITIONS_COLUMN = 4;

    private final MenuServiceClient menuServiceClient;
    private final XSSFWorkbook workbook = new XSSFWorkbook();
    private final XSSFSheet sheet = workbook.createSheet("Walkthroughs");

    @Override
    public boolean support(ExportType type) {
        return ExportType.XLSX.equals(type);
    }

    @Override
    public void exportData(UUID restaurantId, ExportType format, HttpServletResponse response) throws IOException {
        setupResponseHeaders(response);
        MenuAgregateDTO allMenusForRestaurant = menuServiceClient.getMenuAggregate(restaurantId);

        fillHeaders();
        writeDataLines(allMenusForRestaurant);

        try {
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            response.getOutputStream().close();
        }
    }

    private void setupResponseHeaders(HttpServletResponse response) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"restaurant-menus-export.xlsx\"");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
        response.setHeader(HttpHeaders.EXPIRES, "0");
        response.setContentType("application/vnd.ms-excel");
    }

    private void writeDataLines(MenuAgregateDTO allMenusForRestaurant) {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setShrinkToFit(true);

        for (MenuDTO menu : allMenusForRestaurant.getMenus()) {
            Row row = sheet.createRow(rowCount++);
            final XSSFSheet singleMenuSheet = workbook.createSheet(menu.getPublicId().toString());
            createCell(singleMenuSheet, row, 0, menu.getPublicId(), style);


        }
    }


    private void fillHeaders() {
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(sheet, row, MENU_HEADER_COLUMN, "Menu ID", style);
    }

    private void createCell(XSSFSheet singleMenuSheet, Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        switch (value) {
            case Integer valuee -> cell.setCellValue(valuee);
            case Boolean valuee -> cell.setCellValue(valuee);
            case Long valuee -> cell.setCellValue(valuee);
            case LocalDate valuee -> cell.setCellValue(valuee.toString());
            case null, default -> cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
}

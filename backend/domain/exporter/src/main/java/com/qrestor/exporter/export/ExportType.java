package com.qrestor.exporter.export;

public enum ExportType {
    JSON,
    CSV,
    XLSX;

    public static ExportType of(String type) {
        return valueOf(type.toUpperCase());
    }


}

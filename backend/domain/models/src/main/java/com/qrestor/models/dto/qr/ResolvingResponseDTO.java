package com.qrestor.models.dto.qr;

import java.util.UUID;

public record ResolvingResponseDTO(Integer tableId, UUID sellingPointId, UUID menuId) {
}

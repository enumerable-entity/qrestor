package com.qrestor.models.dto.qr;

import java.util.UUID;

public record ResolvingResponseDTO (Integer tableId, UUID restaurantId, UUID menuId) {
}

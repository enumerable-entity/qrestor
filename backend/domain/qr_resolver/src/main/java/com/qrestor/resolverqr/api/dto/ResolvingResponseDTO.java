package com.qrestor.resolverqr.api.dto;

import java.util.UUID;

public record ResolvingResponseDTO (Integer tableId, UUID restaurantId, UUID menuId) {
}

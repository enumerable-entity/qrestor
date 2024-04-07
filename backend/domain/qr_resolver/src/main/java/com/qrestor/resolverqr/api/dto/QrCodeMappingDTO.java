package com.qrestor.resolverqr.api.dto;

import com.qrestor.models.dto.AbstractPublicDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * DTO for {@link com.qrestor.resolverqr.entity.QrCodeMappingEntity}
 */
@Data()
@EqualsAndHashCode(callSuper = true)
public class QrCodeMappingDTO extends AbstractPublicDTO {
    @PositiveOrZero
    private Integer tableId;
    @NotNull
    private UUID sellingPointId;
    private String restaurantName;
    @NotNull
    private UUID menuId;
    private String menuName;
    @NotNull
    private Boolean isActive;
}
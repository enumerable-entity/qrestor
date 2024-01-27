package com.qrestor.resolverqr.api.dto;

import com.qrestor.commons.dto.AbstractPublicDTO;
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
    private UUID restaurantId;
    @NotNull
    private UUID menuId;
    @NotNull
    private Boolean isActive;
}
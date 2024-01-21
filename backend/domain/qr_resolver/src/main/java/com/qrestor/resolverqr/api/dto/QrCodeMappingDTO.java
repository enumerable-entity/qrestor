package com.qrestor.resolverqr.api.dto;

import com.qrestor.commons.dto.AbstractDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * DTO for {@link com.qrestor.resolverqr.entity.QrCodeMappingEntity}
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QrCodeMappingDTO extends AbstractDTO {
    @NotNull
    @NotBlank
    String qrCode;
    @PositiveOrZero
    Integer tableId;
    @NotNull
    UUID restaurantId;
    @NotNull
    UUID menuId;
    @NotNull
    Boolean isActive;
}
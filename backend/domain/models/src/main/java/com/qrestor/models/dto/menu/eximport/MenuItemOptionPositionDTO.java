package com.qrestor.models.dto.menu.eximport;

import com.qrestor.models.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
public class MenuItemOptionPositionDTO extends AbstractPublicDTO {
    String title;
    BigDecimal price;
    boolean isEnabled;
    private UUID itemOptionId;
}

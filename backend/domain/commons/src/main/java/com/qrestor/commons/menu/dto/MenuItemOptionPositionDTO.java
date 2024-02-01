package com.qrestor.commons.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qrestor.commons.dto.AbstractPublicDTO;
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
    @JsonProperty("isEnabled")
    boolean isEnabled;
    private UUID itemOptionId;
}

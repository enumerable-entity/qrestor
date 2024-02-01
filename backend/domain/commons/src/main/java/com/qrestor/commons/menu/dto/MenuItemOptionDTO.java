package com.qrestor.commons.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qrestor.commons.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
public class MenuItemOptionDTO extends AbstractPublicDTO {
    String title;
    @JsonProperty("isMultiSelect")
    private boolean isMultiSelect;
    @JsonProperty("isRequired")
    private boolean isRequired;
    private List<MenuItemOptionPositionDTO> menuItemOptionPositions;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
    private UUID menuItemId;

}

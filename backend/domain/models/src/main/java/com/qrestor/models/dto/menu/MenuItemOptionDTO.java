package com.qrestor.models.dto.menu;

import com.qrestor.models.dto.AbstractPublicDTO;
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

    private boolean isMultiSelect;
    private boolean isRequired;
    private List<MenuItemOptionPositionDTO> menuItemOptionPositions;
    private boolean isEnabled;
    private UUID menuItemId;

}

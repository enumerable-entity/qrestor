package com.qrestor.models.dto.order;

import com.qrestor.models.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class MenuItemOption extends AbstractPublicDTO {
    private String optionTitle;
    private List<OptionPosition> optionPositions;
}

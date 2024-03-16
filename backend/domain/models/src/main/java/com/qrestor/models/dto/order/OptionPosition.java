package com.qrestor.models.dto.order;

import com.qrestor.models.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
public class OptionPosition extends AbstractPublicDTO {
    private String optionTitle;
}

package com.qrestor.models.dto.menu.eximport;

import com.qrestor.models.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class IngredientDTO extends AbstractPublicDTO {
    private String name;
    private boolean isEnabled;
}
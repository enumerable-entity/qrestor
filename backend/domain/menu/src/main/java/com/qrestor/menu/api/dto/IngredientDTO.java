package com.qrestor.menu.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qrestor.menu.entity.IngredientEntity;
import com.qrestor.models.dto.AbstractPublicDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * DTO for {@link IngredientEntity}
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IngredientDTO extends AbstractPublicDTO {
    @NotEmpty
    @NotBlank
    @Length(min = 2, max = 30)
    private String name;
    @JsonProperty("isEnabled")
    private boolean isEnabled;
}
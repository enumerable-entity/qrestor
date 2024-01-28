package com.qrestor.menu.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qrestor.commons.dto.AbstractPublicDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDTO extends AbstractPublicDTO {
    @NotNull
    private UUID restaurantId;
    @NotNull
    @JsonProperty("isActive")
    private Boolean isActive;
    @NotNull
    @NotBlank
    @Length(min = 5, max = 30)
    private String name;
    private String description;
}

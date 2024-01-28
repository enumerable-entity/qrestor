package com.qrestor.menu.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qrestor.commons.dto.AbstractPublicDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Set;

/**
 * DTO for {@link com.qrestor.menu.entity.MenuItemEntity}
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class MenuItemDTO extends AbstractPublicDTO {
    String title;
    String description;
    BigDecimal price;
    boolean isEnabled;
    String imageUrl;
    @NotNull
    ItemCategoryDTO itemCategory;
    Set<IngredientDTO> ingredients;
    @JsonProperty("isVegetarian")
    Boolean isVegetarian;
    @JsonProperty("isVegan")
    Boolean isVegan;
    @JsonProperty("isGlutenFree")
    Boolean isGlutenFree;
    @JsonProperty("isSpicy")
    Boolean isSpicy;
    @JsonProperty("isHalal")
    Boolean isHalal;
    @JsonProperty("isKosher")
    Boolean isKosher;
    @JsonProperty("isTreeNuts")
    Boolean isNuts;
    @JsonProperty("isDairy")
    Boolean isDairy;
    @JsonProperty("isEggs")
    Boolean isEggs;
    @JsonProperty("isFish")
    Boolean isFish;
    @JsonProperty("isShellfish")
    Boolean isShellfish;
    @JsonProperty("isSoy")
    Boolean isSoy;
    private MenuDTO menu;
}
package com.qrestor.models.dto.menu.eximport;

import com.qrestor.models.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@EqualsAndHashCode(callSuper = false)
@Data
public class MenuItemDTO extends AbstractPublicDTO {

    String title;
    String description;
    BigDecimal price;
    boolean isEnabled;
    String imageUrl;
    ItemCategoryDTO itemCategory;
    Set<IngredientDTO> ingredients;
    Boolean isVegetarian;
    Boolean isVegan;
    Boolean isGlutenFree;
    Boolean isSpicy;
    Boolean isHalal;
    Boolean isKosher;
    Boolean isNuts;
    Boolean isDairy;
    Boolean isEggs;
    Boolean isFish;
    Boolean isShellfish;
    Boolean isSoy;
    private List<MenuItemOptionDTO> options;

}
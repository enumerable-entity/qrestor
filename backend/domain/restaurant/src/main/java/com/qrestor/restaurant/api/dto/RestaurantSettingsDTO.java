package com.qrestor.restaurant.api.dto;

import com.qrestor.commons.dto.BasicDTO;
import com.qrestor.restaurant.entity.RestaurantSettingsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO for {@link RestaurantSettingsEntity}
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class RestaurantSettingsDTO extends BasicDTO {
    String backgroundImageUrl;
    String primaryColor;
    String secondaryColor;
    String logoUrl;
}
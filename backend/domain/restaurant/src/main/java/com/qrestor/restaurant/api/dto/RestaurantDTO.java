package com.qrestor.restaurant.api.dto;

import com.qrestor.models.dto.AbstractPublicDTO;
import com.qrestor.restaurant.entity.RestaurantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO for {@link RestaurantEntity}
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantDTO extends AbstractPublicDTO {
    String name;
    String title;
    String description;
    String address;
    String phone;
    RestaurantSettingsDTO settings;
}
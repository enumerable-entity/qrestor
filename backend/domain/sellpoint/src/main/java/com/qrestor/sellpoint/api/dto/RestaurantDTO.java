package com.qrestor.sellpoint.api.dto;

import com.qrestor.models.dto.AbstractPublicDTO;
import com.qrestor.models.dto.auth.AddressDTO;
import com.qrestor.sellpoint.entity.SellingPointEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO for {@link SellingPointEntity}
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantDTO extends AbstractPublicDTO {
    String name;
    String title;
    String description;
    AddressDTO address;
    String phone;
    RestaurantSettingsDTO settings;
}
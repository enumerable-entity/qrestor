package com.qrestor.sellpoint.api.dto;

import com.qrestor.models.dto.BasicDTO;
import com.qrestor.sellpoint.entity.SellingPointSettingsEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO for {@link SellingPointSettingsEntity}
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class RestaurantSettingsDTO extends BasicDTO {
    String backgroundImageUrl;
    String primaryColor;
    String secondaryColor;
    String logoUrl;
    String topHeaderUrl;
}
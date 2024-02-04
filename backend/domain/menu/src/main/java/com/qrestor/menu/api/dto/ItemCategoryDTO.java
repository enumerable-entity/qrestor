package com.qrestor.menu.api.dto;

import com.qrestor.models.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO for {@link com.qrestor.menu.entity.ItemCategoryEntity}
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ItemCategoryDTO extends AbstractPublicDTO {
    String nlsKey;
    String iconCode;
}
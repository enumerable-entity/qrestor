package com.qrestor.menu.api.dto;

import com.qrestor.commons.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * DTO for {@link com.qrestor.menu.entity.ItemCategoryEntity}
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ItemCategoryDTO extends AbstractPublicDTO {
    UUID publicId;
    String nlsKey;
    String iconCode;
}
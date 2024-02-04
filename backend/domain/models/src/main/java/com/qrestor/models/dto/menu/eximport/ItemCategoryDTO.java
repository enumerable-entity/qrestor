package com.qrestor.models.dto.menu.eximport;

import com.qrestor.models.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class ItemCategoryDTO extends AbstractPublicDTO {
    String nlsKey;
    String iconCode;
}
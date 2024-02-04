package com.qrestor.models.dto.menu.eximport;
import com.qrestor.models.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class MenuDTO extends AbstractPublicDTO {

    private UUID restaurantId;
    private Boolean isActive;
    private String name;
    private String description;

    private List<MenuItemDTO> menuItems;
}

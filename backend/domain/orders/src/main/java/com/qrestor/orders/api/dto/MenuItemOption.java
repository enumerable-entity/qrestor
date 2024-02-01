package com.qrestor.orders.api.dto;

import com.qrestor.commons.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class MenuItemOption extends AbstractPublicDTO {
    private List<UUID> optionPositions;
}

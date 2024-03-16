package com.qrestor.models.dto.order;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.util.List;
import java.util.UUID;

@Data
@FieldNameConstants
public class ItemOrderDetails {
    private UUID menuItemId;
    private String menuItemTitle;
    private List<MenuItemOption> menuItemOptions;
    private int quantity;
    private String specialInstructions;
}

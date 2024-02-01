package com.qrestor.orders.api.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ItemOrderDetails {
    private UUID menuItemId;
    private List<MenuItemOption> menuItemOptions;
    private int quantity;
    private String specialInstructions;
}

package com.qrestor.orders.api.dto;

import com.qrestor.commons.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderDTO extends AbstractPublicDTO {
    private UUID restaurantId;
    private int tableNumber;
    private List<ItemOrderDetails> items;

}

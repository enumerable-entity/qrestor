package com.qrestor.models.dto.order;

import com.qrestor.models.dto.AbstractPublicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
public class OrderDTO extends AbstractPublicDTO {
    private UUID restaurantId;
    private int tableNumber;
    private List<ItemOrderDetails> items;

}

package com.qrestor.menu.api.dto.list;

import com.qrestor.models.dto.BasicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false, of = {"itemId"})
public class MenuItemListDTO extends BasicDTO {
    private final UUID itemId;
    private final UUID categoryId;
    private final String title;
    private final String description;
    private final String imageUrl;
    private final Long price;
    private final List<String> ingredients;
}

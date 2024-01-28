package com.qrestor.menu.api.dto.list;

import com.qrestor.commons.dto.BasicDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false, of = {"categoryId"})
@Data
public class MenuListDTO extends BasicDTO {
    private final String categoryNlsKey;
    private final UUID categoryId;
    private final String categoryIcon;
    private final List<MenuItemListDTO> items;
}

package com.qrestor.models.dto.menu.eximport;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuAgregateDTO implements Serializable {
    List<MenuDTO> menus;
}

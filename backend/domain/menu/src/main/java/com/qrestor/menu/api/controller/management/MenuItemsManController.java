package com.qrestor.menu.api.controller.management;

import com.qrestor.commons.CrudController;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.api.dto.MenuItemDTO;
import com.qrestor.menu.service.MenuItemsService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RestEndpoints.MENU_ITEMS_MANAGEMENT)
@PreAuthorize("hasRole('RESTAURATEUR')")
public class MenuItemsManController extends CrudController<MenuItemDTO> {

    private final MenuItemsService menuItemsService;

    public MenuItemsManController(MenuItemsService crudService) {
        super(crudService);
        this.menuItemsService = crudService;
    }

    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<MenuItemDTO>> findAllByMenuId(@PageableDefault(size = 30) Pageable pageable,
                                                             @PathVariable UUID menuId) {
        return ResponseEntity.ok(menuItemsService.findAllByMenuId(pageable, menuId, false));
    }

}

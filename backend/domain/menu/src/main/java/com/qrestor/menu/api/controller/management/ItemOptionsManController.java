package com.qrestor.menu.api.controller.management;

import com.qrestor.commons.CrudController;
import com.qrestor.commons.menu.dto.MenuItemOptionDTO;
import com.qrestor.menu.api.RestEndpoints;
import com.qrestor.menu.service.MenuItemOptionsService;
import com.qrestor.menu.service.impl.MenuItemOptionsServiceImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(RestEndpoints.MENU_ITEM_OPTIONS_MANAGEMENT)
@PreAuthorize("hasRole('RESTAURATEUR')")
public class ItemOptionsManController extends CrudController<MenuItemOptionDTO> {

    private final MenuItemOptionsService menuItemsService;

    public ItemOptionsManController(MenuItemOptionsServiceImpl crudService) {
        super(crudService);
        this.menuItemsService = crudService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<MenuItemOptionDTO>> findAllByMenuItemId(@PageableDefault(size = 30) Pageable pageable,
                                                                       @RequestParam(required = false) UUID menuItemId) {
        return ResponseEntity.ok(menuItemsService.findAllByMenuItemId(pageable, menuItemId, false));
    }
}
